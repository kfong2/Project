package com.example.project.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.components.AccountGreeting
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.components.LandingButtonComponent
import com.example.project.components.ProfileInfoItem
import com.example.project.data.RegistrationViewModel
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.navigation.defaultNavItems
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    uid: String
) {
    var uid = uid
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var registrationDate by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableIntStateOf(0) }

    // Fetch user data from Firebase
    LaunchedEffect("fetchUserData", uid) {
        getUserDataFromFirebase(uid) { fetchedUserRecord ->
            fetchedUserRecord?.let {
                firstName = it.firstName
                lastName = it.lastName
                email = it.email
                registrationDate = it.registrationDate
                accumulatedPoints = it.accumulatedPoints
                Log.d(
                    ContentValues.TAG,
                    "Fetched user data: firstName=$firstName, accumulatedPoints=$accumulatedPoints, uid: $uid"
                )
            }
        }
    }

    var bottomNavState by rememberSaveable { mutableIntStateOf(2) }
    var context = LocalContext.current

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Account",
                logoutButtonClicked = {
                    registrationViewModel.logout()
                },
                navController
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = defaultNavItems,
                selectedIndex = bottomNavState,
                onItemSelected = { index ->
                    bottomNavState = index
                    /*                    when (defaultNavItems[index].title) {
                                            "Dashboard" -> navController.navigate("Dashboard/$uid")
                                            "Rewards" -> navController.navigate("Rewards/$uid")
                                            "Account" -> navController.navigate("Account/$uid")
                                        }*/

                    when (defaultNavItems[index].title) {
                        context.resources.getString(R.string.nav_dashboard) -> navController.navigate(
                            context.resources.getString(R.string.nav_dashboard) + "/$uid"
                        )

                        context.resources.getString(R.string.nav_rewards) -> navController.navigate(
                            context.resources.getString(R.string.nav_rewards) + "/$uid"
                        )

                        context.resources.getString(R.string.nav_account) -> navController.navigate(
                            context.resources.getString(R.string.nav_account) + "/$uid"
                        )
                    }


                }
            )
        }
    ) { contentPadding ->

        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        AccountGreeting(firstName = firstName, points = accumulatedPoints)

                        Spacer(modifier = Modifier.height(20.dp))

                        // Display other user data
                        ProfileInfoItem("First Name", firstName)
                        ProfileInfoItem("Last Name", lastName)
                        ProfileInfoItem("Email", email)
                        ProfileInfoItem("Registration Date", registrationDate)
                    }
                }


                // Button: Navigate to MyRewards Screen
                LandingButtonComponent(
                    value = "My Rewards",
                    iconName = Icons.AutoMirrored.Filled.List,
                    onButtonClicked = { navController.navigate("MyRewards/$uid") },
                    isEnabled = true
                )

                // Button: Navigate to Redemption History
                LandingButtonComponent(
                    value = "View Redemption History",
                    iconName = Icons.AutoMirrored.Filled.List,
                    onButtonClicked = { navController.navigate("Transaction/$uid") },
                    isEnabled = true
                )
            }
        }
    }
}



