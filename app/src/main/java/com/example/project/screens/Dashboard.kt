package com.example.project.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.components.AppToolbar
import com.example.project.components.GeneralGreeting
import com.example.project.components.HeadingComponent
import com.example.project.components.LandingButtonComponent
import com.example.project.components.RewardsLazyRow
import com.example.project.components.RewardsTextButtonComponent
import com.example.project.components.TextButtonComponent
import com.example.project.components.TitleComponent
import com.example.project.components.WelcomeBackComponent
import com.example.project.data.RegistrationViewModel
import com.example.project.data.RewardData
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.functions.getRewardsDataFromFirebase

data class NavItemState(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    uid: String
) {

    var uid = uid

    var firstName by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableStateOf(0) }

    val rewardsList by remember { mutableStateOf(mutableListOf<RewardData>()) }

    // Fetch user data from Firebase when the screen is created
    LaunchedEffect(uid) {

        Log.d(TAG, "LaunchedEffect - UID: $uid")
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
                        onClick = {
                            when (item.title) {
                                "Dashboard" -> navController.navigate("Dashboard/$uid")
                                "Register" -> navController.navigate("Register/$uid")
                                "Account" -> navController.navigate("Account/$uid")
                            }
                        },

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
                GeneralGreeting(firstName = firstName, points = accumulatedPoints)

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
//                    Log.d("Navigation", "Navigating to Redeem with rewardId: $rewardId")
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