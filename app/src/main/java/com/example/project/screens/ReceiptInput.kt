package com.example.project.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project.data.UserRecord
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.functions.updateAccumulatedPoints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReceiptInput(uid: String, navController: NavController) {
    var uid = uid

    var accumulatedPoints by remember { mutableStateOf(0) }
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

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input field for Purchase Date
        DateInputTextField()


        Spacer(modifier = Modifier.height(16.dp))

        // Button to submit purchase details
        Button(
            onClick = {
                // Update Firebase receipts collection
                updateReceiptsCollection(
                    shopName = shopName,
                    uid = uid,
                    purchaseAmount = purchaseAmount.toInt(),
                    purchaseDate = purchaseDate
                )
                val newPoints = accumulatedPoints + purchaseAmount.toInt()
                updateAccumulatedPoints(uid, newPoints, onUpdatePoints = {})

                navController.navigate("Dashboard/$uid")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Submit")
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
fun DateInputTextField() {
    var dateInputted by remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        value = dateInputted,
        onValueChange = {
            dateInputted = it
        },
        label = { Text("Purchase Date") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("yyyy-mm-dd") }
    )
}


private fun generateUniqueReceiptId(): String {
    val timestamp = Timestamp.now().toDate().time
    val uniqueIdentifier = generateRandomString()
    return "$timestamp-$uniqueIdentifier"
}

private fun generateRandomString(length: Int = 8): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}

private fun updateReceiptsCollection(
    shopName: String,
    uid: String,
    purchaseAmount: Int,
    purchaseDate: Date
) {
    val receiptId = generateUniqueReceiptId()
    val receiptDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(purchaseDate)

    val database = FirebaseDatabase.getInstance()
    val receiptsRef = database.getReference("receipts")
    val receiptRef = receiptsRef.child(receiptId)

    receiptRef.child("receiptId").setValue(receiptId)
    receiptRef.child("uid").setValue(uid)
    receiptRef.child("shopName").setValue(shopName)
    receiptRef.child("purchaseAmount").setValue(purchaseAmount)
    receiptRef.child("purchaseDate").setValue(receiptDate)
}