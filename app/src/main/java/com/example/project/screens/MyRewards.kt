package com.example.project.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.components.GeneralGreeting
import com.example.project.components.myRewardsLazyColumn
import com.example.project.data.MyRewardData
import com.example.project.data.RegistrationViewModel
import com.example.project.functions.getMyRewardsDataFromFirebase
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.navigation.defaultNavItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRewards(
    registrationViewModel: RegistrationViewModel,
    uid: String,
    navController: NavHostController
) {
    var uid = uid
    var firstName by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableIntStateOf(0) }

    // Fetch user data from Firebase
    LaunchedEffect("fetchUserData", uid) {
        getUserDataFromFirebase(uid) { fetchedUserRecord ->
            fetchedUserRecord?.let {
                firstName = it.firstName
                accumulatedPoints = it.accumulatedPoints
                Log.d(
                    ContentValues.TAG,
                    "Fetched user data: firstName=$firstName, accumulatedPoints=$accumulatedPoints, uid: $uid"
                )
            }
        }
    }

    val myRewardsList by remember { mutableStateOf(mutableListOf<MyRewardData>()) }

    // Fetch user data from Firebase when the screen is first created
    LaunchedEffect(uid) {
        getMyRewardsDataFromFirebase(uid) { myRewardsData ->
            myRewardsList.clear()
            myRewardsList.addAll(myRewardsData)
        }
    }

    var bottomNavState by rememberSaveable { mutableIntStateOf(2) }
    var context = LocalContext.current

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "My Rewards",
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

                    when(defaultNavItems[index].title){
                        context.resources.getString(R.string.nav_dashboard) -> navController.navigate(context.resources.getString(R.string.nav_dashboard) + "/$uid")
                        context.resources.getString(R.string.nav_rewards) -> navController.navigate(context.resources.getString(R.string.nav_rewards) + "/$uid")
                        context.resources.getString(R.string.nav_account) -> navController.navigate(context.resources.getString(R.string.nav_account) + "/$uid")
                    }
                }
            )
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(contentPadding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(
                    ) {
                        GeneralGreeting(firstName = firstName, points = accumulatedPoints)
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        if (myRewardsList.isNotEmpty()) {
                            myRewardsLazyColumn(myRewardsList = myRewardsList, uid = uid, navController)
                        } else {
                            // Display a message when the list is empty
                            Text(
                                text = "You have no rewards.",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}