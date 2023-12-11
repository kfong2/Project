package com.example.project.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.components.GeneralGreeting
import com.example.project.components.RewardInfoCard
import com.example.project.components.TitleComponent
import com.example.project.data.RegistrationViewModel
import com.example.project.data.RewardData
import com.example.project.data.UserRecord
import com.example.project.functions.getRewardsDataFromFirebaseWithKeys
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.navigation.defaultNavItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Redeem(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    rewardId: String,
    uid: String,
    onBackClicked: () -> Unit
) {
    var uid = uid
    var firstName by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableIntStateOf(0) }
    var selectedReward by remember { mutableStateOf<RewardData?>(null) }
    var userRecord by remember { mutableStateOf<UserRecord?>(null) }
    var rewardsList by remember { mutableStateOf<List<RewardData>>(emptyList()) }

    var selectedRewardKey by remember { mutableStateOf("") }
    var selectedRewardId by remember { mutableStateOf("") }
    var selectedRewardName by remember { mutableStateOf("") }
    var selectedRequiredPoints by remember { mutableIntStateOf(0) }
    var selectedRewardQuantity by remember { mutableIntStateOf(0) }


    // Fetch user data from Firebase when the screen is created
    LaunchedEffect("fetchUserData", uid) {
        getUserDataFromFirebase(uid) { fetchedUserRecord ->
            fetchedUserRecord?.let {
                firstName = it.firstName
                accumulatedPoints = it.accumulatedPoints
                userRecord = it
            }
        }
    }

    // Fetch selected reward data based on rewardId
    LaunchedEffect("fetchRewardsData") {
        getRewardsDataFromFirebaseWithKeys { fetchedRewardsList ->
            rewardsList = fetchedRewardsList.map { it.second }
            val selectedRewardPair = fetchedRewardsList.find { it.second.rewardId == rewardId }
            selectedRewardPair?.let { (rewardKey, reward) ->
                selectedReward = reward
                selectedRewardKey = rewardKey
                selectedRewardId = reward.rewardId
                selectedRewardName = reward.rewardName
                selectedRequiredPoints = reward.requiredPoints
                selectedRewardQuantity = reward.quantity
            }
        }
    }

    var bottomNavState by rememberSaveable { mutableIntStateOf(100) }

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Redeem Rewards",
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
                    when (defaultNavItems[index].title) {
                        "Dashboard" -> navController.navigate("Dashboard/$uid")
                        "Rewards" -> navController.navigate("Rewards/$uid")
                        "Account" -> navController.navigate("Account/$uid")
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
                GeneralGreeting(firstName = firstName, points = accumulatedPoints)

                Spacer(modifier = Modifier.height(5.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 5.dp),
                    elevation = CardDefaults.cardElevation(),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    TitleComponent("Redeem Reward")
                }

                RewardInfoCard(
                    uid = uid,
                    accumulatedPoints = accumulatedPoints,
                    rewardKey = selectedRewardKey,
                    rewardId = selectedRewardId,
                    rewardName = selectedRewardName,
                    requiredPoints = selectedRequiredPoints,
                    rewardQuantity = selectedRewardQuantity,
                    navController,
                    onUpdatePoints = {}
                )
            }
        }
    }
}
