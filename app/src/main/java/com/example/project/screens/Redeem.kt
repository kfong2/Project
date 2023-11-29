package com.example.project.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.components.AppToolbar
import com.example.project.components.GeneralGreeting
import com.example.project.components.RewardInfoCard
import com.example.project.components.TitleComponent
import com.example.project.data.RegistrationViewModel
import com.example.project.data.RewardData
import com.example.project.data.UserRecord
import com.example.project.functions.getRewardsDataFromFirebaseWithKeys
import com.example.project.functions.getUserDataFromFirebase


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
    var accumulatedPoints by remember { mutableStateOf(0) }
    var selectedReward by remember { mutableStateOf<RewardData?>(null) }
    var userRecord by remember { mutableStateOf<UserRecord?>(null) }
    var rewardsList by remember { mutableStateOf<List<RewardData>>(emptyList()) }

    var selectedRewardKey by remember { mutableStateOf("") }
    var selectedRewardId by remember { mutableStateOf("") }
    var selectedRewardName by remember { mutableStateOf("") }
    var selectedRequiredPoints by remember { mutableStateOf(0) }
    var selectedRewardQuantity by remember { mutableStateOf(0) }


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
