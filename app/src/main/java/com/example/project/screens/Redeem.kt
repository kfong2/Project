package com.example.project.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.example.project.components.AppToolbar
import com.example.project.components.ButtonComponent
import com.example.project.components.HeadingComponent
//import com.example.project.components.RewardInfoCard
import com.example.project.components.UserInfoComponent
import com.example.project.components.WelcomeBackComponent
import com.example.project.data.RewardData
import com.example.project.functions.getRewardsDataFromFirebase
import com.example.project.data.RegistrationViewModel
import com.example.project.data.UserRecord
import com.example.project.functions.getUserDataFromFirebase
//import com.example.project.functions.updateAccumulatedPointsInFirebase
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Redeem(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    rewardId: String,
    uid: String,
    onBackClicked: () -> Unit
) {
    var uid by rememberSaveable { mutableStateOf(uid) }
    var firstName by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableStateOf(0) }
    var selectedReward by remember { mutableStateOf<RewardData?>(null) }
    var userRecord by remember { mutableStateOf<UserRecord?>(null) }
    var rewardsList by remember { mutableStateOf<List<RewardData>>(emptyList()) }

    // Fetch user data from Firebase when the screen is first created
    LaunchedEffect("fetchUserData", uid) {
        getUserDataFromFirebase(uid) { fetchedUserRecord ->
            fetchedUserRecord?.let {
                firstName = it.firstName
                accumulatedPoints = it.accumulatedPoints
                userRecord = it // Update the outer userRecord variable
            }
        }
    }

    // Fetch selected reward data based on rewardId
    LaunchedEffect(rewardId) {
        getRewardsDataFromFirebase { fetchedRewardsList ->
            rewardsList = fetchedRewardsList
            selectedReward = rewardsList.find { it.rewardId == rewardId }
        }
    }

    val items = listOf(
        NavItemState(
            title = "Dashboard",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavItemState(
            title = "Register",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email
        ),
        NavItemState(
            title = "Account",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face
        )
    )

    var bottomNavState by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "PointGrow",
                logoutButtonClicked = {
                    registrationViewModel.logout()
                }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp)),
                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = .5f)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = bottomNavState == index,
                        onClick = { bottomNavState = index },
                        icon = {
                            Icon(
                                imageVector = if (bottomNavState == index) item.selectedIcon
                                else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(text = item.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = Color(0xFF131F0D)
                        )
                    )
                }
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                UserInfoComponent(firstName = firstName, points = accumulatedPoints)

                HeadingComponent("Redeem Reward")

                // Display the reward information
                selectedReward?.let { reward ->
                    RewardInfoCard(
                        reward = reward,
                        userRecord = userRecord ?: UserRecord(),
                        uid = uid,
                        onUpdatePoints = { newPoints ->
                            // Update the local state
                            accumulatedPoints = newPoints
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardInfoCard(
    reward: RewardData,
    userRecord: UserRecord,
    uid: String,
    onUpdatePoints: (Int) -> Unit
) {
    var redeemQuantity by remember { mutableStateOf(0) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Filled.Discount, contentDescription = "",  modifier = Modifier.size(48.dp))
            Text(
                text = try {
                    "${reward.rewardName}"
                } catch (e: Exception) {
                    // Log the exception
                    e.printStackTrace()
                    "Reward Name: N/A"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Required Points: ${reward.requiredPoints}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${reward.quantity} left", fontSize = 14.sp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
//                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { if (redeemQuantity > 0) redeemQuantity-- },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease Quantity")
                }

                // Input Box
                OutlinedTextField(
                    value = redeemQuantity.coerceAtMost(5).toString(),
//                    value = redeemQuantity.toString(),
                    onValueChange = {
                        // Handle the case when the user enters non-numeric characters
                        if (it.isDigitsOnly()) {
//                            redeemQuantity = it.toInt()
                            redeemQuantity = it.toInt().coerceIn(0, minOf(5, reward.quantity))
                        }
                    },
                    label = { Text("Redeem Quantity") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .width(150.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                )

                IconButton(
                    onClick = { redeemQuantity++ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Increase Quantity")
                }
            }

            ButtonComponent(
                value = "Redeem",
                iconName = Icons.Default.AddCircle,
                onButtonClicked = {
                    // Check if the user has enough points
                    val requiredPoints = redeemQuantity * reward.requiredPoints
                    errorMessage = if (requiredPoints > userRecord.accumulatedPoints) {
                        "Not enough points to redeem"
                    } else {
                        // Deduct points and update user data in Firebase
                        val newPoints = userRecord.accumulatedPoints - requiredPoints
                        updateAccumulatedPointsInFirebase(uid, newPoints) { success ->
                            if (success) {
                                // Update the local state
                                onUpdatePoints(newPoints)
                            } else {
                                errorMessage = "Failed to update points"
                            }
                        }
                        null
                    }

                },
                isEnabled = redeemQuantity > 0,
                errorMessage = errorMessage
            )
        }
    }
}

fun updateAccumulatedPointsInFirebase(uid: String, newPoints: Int, onComplete: (Boolean) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val data = hashMapOf("accumulatedPoints" to newPoints)

    db.collection("users").document(uid).set(data)
        .addOnSuccessListener {
            Log.d(ContentValues.TAG, "User data updated successfully")
            onComplete(true) // Notify success
        }
        .addOnFailureListener { e ->
            Log.e(ContentValues.TAG, "Error updating user data", e)
            onComplete(false) // Notify failure
        }
}