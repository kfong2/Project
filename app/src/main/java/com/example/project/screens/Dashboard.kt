package com.example.project.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.BuildConfig
import com.example.project.R
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.components.GeneralGreeting
import com.example.project.components.LandingButtonComponent
import com.example.project.components.RewardsLazyRow
import com.example.project.components.RewardsTextButtonComponent
import com.example.project.api.TemperatureComponent
import com.example.project.components.TitleComponent
import com.example.project.api.fetchTemperature
import com.example.project.data.RegistrationViewModel
import com.example.project.data.RewardData
import com.example.project.functions.getRewardsDataFromFirebase
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.navigation.defaultNavItems

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    uid: String
) {
    var uid = uid
    var firstName by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableIntStateOf(0) }

    val rewardsList by remember { mutableStateOf(mutableListOf<RewardData>()) }

    // Fetch user data from Firebase when the screen is created
    LaunchedEffect(uid) {
        getUserDataFromFirebase(uid) { userRecord ->
            userRecord?.let {
                firstName = it.firstName
                accumulatedPoints = it.accumulatedPoints

                Log.d(
                    TAG,
                    "Fetched user data: firstName=$firstName, accumulatedPoints=$accumulatedPoints, uid: $uid"
                )
            }
        }

        getRewardsDataFromFirebase { rewardsData ->
            rewardsList.clear()
            rewardsList.addAll(rewardsData)
        }
    }

    val APIKEY = BuildConfig.API_KEY

    // State for temperature data
    var temperature by remember { mutableStateOf(0.0) }
    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        fetchTemperature(
            latitude = 49.8844,
            longitude = -97.147,
            onSuccess = { newTemperature ->
                // Update the state variable or perform any action with the new temperature
                temperature = newTemperature
                isLoading = false
            },
            onError = {
                // Handle the case where fetching the temperature failed
                Log.e("Temperature", "Failed to fetch current temperature")
                isLoading = false
            },
            apiKey = APIKEY,
            units = "metric"
        )
    }

    var bottomNavState by rememberSaveable { mutableIntStateOf(0) }
    var context = LocalContext.current

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Point Grow",
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
                    when(defaultNavItems[index].title){
                        context.resources.getString(R.string.nav_dashboard) -> navController.navigate(context.resources.getString(
                            R.string.nav_dashboard) + "/$uid")
                        context.resources.getString(R.string.nav_rewards) -> navController.navigate(context.resources.getString(
                            R.string.nav_rewards) + "/$uid")
                        context.resources.getString(R.string.nav_account) -> navController.navigate(context.resources.getString(
                            R.string.nav_account) + "/$uid")
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
                    .padding(contentPadding)
            ) {
                TemperatureComponent(temperature, isLoading, APIKEY, {})

                GeneralGreeting(firstName = firstName, points = accumulatedPoints)

//                TemperatureComponent(temperature, isLoading, APIKEY, {})

                Spacer(modifier = Modifier.height(5.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 5.dp),
                    elevation = CardDefaults.cardElevation(),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    TitleComponent("Latest Rewards")

                    RewardsLazyRow(rewardsList = rewardsList, onItemClick = { reward ->
                    Log.d("Navigation", "Navigating to Redeem with rewardId: ${reward.rewardId}")
                        if (reward != null) {
                            navController.navigate("Redeem/${reward.rewardId}/$uid")
                        } else {
                            // Handle the case where rewardId is null or empty
                            navController.navigate("Rewards/$uid")
                            Log.e("Navigation", "Invalid rewardId: ${reward.rewardId}")
                        }
                    })

                    RewardsTextButtonComponent(
                        action = { navController.navigate("Rewards/$uid") },
                        buttonText = "See all Rewards"
                    )
                    LandingButtonComponent(value = "Input Receipt", iconName = Icons.Default.AddChart, onButtonClicked = { navController.navigate("ReceiptInput/$uid") }, isEnabled = true )
                }
            }
        }
    }
}


