package com.example.project.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.data.RegistrationViewModel
import com.example.project.data.UserRecord
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.functions.updateAccumulatedPoints
import com.example.project.functions.updateReceiptsCollection
import com.example.project.navigation.defaultNavItems
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReceiptInput(
    registrationViewModel: RegistrationViewModel, uid: String, navController: NavController) {
    var uid = uid

    var accumulatedPoints by remember { mutableIntStateOf(0) }
    var userRecord by remember { mutableStateOf<UserRecord?>(null) }


    // Fetch user data from Firebase when the screen is created
    LaunchedEffect("fetchUserData", uid) {
        getUserDataFromFirebase(uid) { fetchedUserRecord ->
            fetchedUserRecord?.let {
                accumulatedPoints = it.accumulatedPoints
                userRecord = it
            }
        }
    }

    var shopName by remember { mutableStateOf("") }
    var purchaseAmount by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf(Date()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var bottomNavState by rememberSaveable { mutableIntStateOf(100) }

    var totalAmountRecognized by remember { mutableStateOf("") }

    // Update the state based on the recognized amount in onAmountRecognized
    val onAmountRecognized: (String) -> Unit = { totalAmount ->
        // Handle the recognized total amount, Update the purchaseAmount field with the recognized amount
        purchaseAmount = totalAmount
        totalAmountRecognized = totalAmount
    }
    val contentResolver = LocalContext.current.contentResolver


    // Create an ActivityResultLauncher for capturing images
    val getContent: ActivityResultLauncher<String> =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            // Handle the captured image URI
            uri?.let { imageUri ->
                val bitmap = loadBitmapFromUri(imageUri, contentResolver)
                processImage(bitmap) { totalAmount ->
                    // Handle the recognized total amount
                    onAmountRecognized(totalAmount)
                }
            }
        }

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Submit Receipt",
                logoutButtonClicked = {
                    registrationViewModel.logout()
                },
                navController,
                isDashboardScreen = false
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = defaultNavItems,
                selectedIndex = bottomNavState,
                onItemSelected = { index ->
                    bottomNavState = index
                    when (defaultNavItems[index].title) {
                        "Dashboard" -> navController.navigate("Dashboard/$uid")
                        "Rewards" -> navController.navigate("Rewards/$uid")
                        "Account" -> navController.navigate("Account/$uid")
                    }
                }
            )
        }
    ) {contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            // Dropdown for shopName
            ShopNameDropdown(
                selectedShopName = shopName,
                onShopNameSelected = { selectedShopName ->
                    shopName = selectedShopName
                    keyboardController?.hide()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input field for purchaseAmount
            OutlinedTextField(
                value = purchaseAmount,
                onValueChange = { purchaseAmount = it },
                label = { Text("Purchase Amount") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    // Icon to trigger image capture for OCR
                    IconButton(
                        onClick = {
                            // Trigger image capture
                            getContent.launch("image/*")
                        }
                    ) {
                        Icon(Icons.Default.Image, contentDescription = "Image")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input field for Purchase Date
            DateInputTextField { selectedDate ->
                purchaseDate = selectedDate
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to submit purchase details
            Button(
                onClick = {
                    // Update Firebase receipts collection
                    updateReceiptsCollection(
                        shopName = shopName,
                        uid = uid,
                        purchaseAmount = purchaseAmount.toDouble().toInt(),
                        purchaseDate = purchaseDate
                    )
                    val newPoints = accumulatedPoints + purchaseAmount.toDouble().toInt()
                    updateAccumulatedPoints(uid, newPoints, onUpdatePoints = {})
                    navController.navigate("ReceiptSubmitted/$uid")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Submit")
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShopNameDropdown(
    selectedShopName: String,
    onShopNameSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var shopNames by remember { mutableStateOf(emptyList<String>()) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(context) {
        val database = FirebaseDatabase.getInstance().reference.child("shops")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val names = snapshot.children.mapNotNull { it.child("shopName").value as? String }
                shopNames = names
                Log.d("ShopNameDropdown", "Shop names: $names")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ShopNameDropdown", "Error fetching shop names: $error")
            }
        }

        database.addListenerForSingleValueEvent(listener)

        onDispose {
            database.removeEventListener(listener)
        }
    }

    Box {
        OutlinedTextField(
            value = if (expanded) "" else selectedShopName,
            onValueChange = {},
            label = { Text("Shop Name") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        expanded = !expanded
                        keyboardController?.hide()
                    }
                ) {
                    Icon(Icons.Outlined.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                keyboardController?.hide()
            },
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            shopNames.forEachIndexed { index, name ->
                DropdownMenuItem(
                    text = {Text(text = name, color = Color.Black) },
                    onClick = {
                        onShopNameSelected(name)
                        selectedIndex = index
                        expanded = false
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInputTextField(onDateSelected: (Date) -> Unit) {
    var dateInputted by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    //Showing date picker dialog
    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val calendar = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val formattedDate = dateFormatter.format(calendar.time)
                dateInputted = TextFieldValue(formattedDate)
                onDateSelected(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = dateInputted,
            onValueChange = {
                dateInputted = it
                // Parse the date and invoke the callback
                try {
                    val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.text)
                    if (parsedDate != null) {
                        onDateSelected(parsedDate)
                    }
                } catch (e: ParseException) {
                    //
                }
            },
            label = { Text("Purchase Date") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            placeholder = { Text("yyyy-mm-dd") }
        )

        IconButton(
            onClick = { showDatePickerDialog() },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Picker"
            )
        }
    }
}

private fun loadBitmapFromUri(uri: Uri, contentResolver: ContentResolver): Bitmap {
    val inputStream: InputStream = contentResolver.openInputStream(uri)!!
    return BitmapFactory.decodeStream(inputStream)
}


private fun processImage(bitmap: Bitmap, onAmountRecognized: (String) -> Unit) {
    val image = InputImage.fromBitmap(bitmap, 0)

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val result = recognizer.process(image)
        .addOnSuccessListener { visionText ->
            // Process the recognized text
            val totalAmount = findTotalAmount(visionText)
            onAmountRecognized(totalAmount) // Pass the recognized amount to the callback
        }
        .addOnFailureListener { e ->
            // Handle failure
            e.printStackTrace()
            // If there is a failure, set to 0.00
            onAmountRecognized("0.00")
        }
}


private fun findTotalAmount(visionText: Text): String {
    // Get all recognized blocks of text
    val blocks = visionText.textBlocks
    var largestFontSize = 0.0
    var total = "0.00"

    // Iterate through text blocks to find the total amount
    for (block in blocks) {
        // Iterate through lines in each block
        for (line in block.lines) {
            val elements = line.elements

            // Log the entire content of the line
            Log.d("OCR_DEBUG", "Line Content: ${line.text}")

            // Iterate through elements in each line
            for (element in elements) {
                // Log the content and font size of the element
                Log.d("OCR_DEBUG", "Element Content: ${element.text}, Font Size: ${element.boundingBox?.height()}")

                // Check if the element is a double number and has a larger font size
                val fontSize = element.boundingBox?.height()?.toDouble() ?: 0.0
                if (element.text.matches(Regex("\\d+\\.\\d+")) && fontSize > largestFontSize) {
                    // Update the largest font size and total
                    largestFontSize = fontSize
                    total = element.text
                }
            }
        }
    }

    // Log the final result for debugging
    Log.d("OCR_DEBUG", "Total with Largest Font Size: $total, Font Size: $largestFontSize")

    // Return a default value if no total amount is found
    return total
}