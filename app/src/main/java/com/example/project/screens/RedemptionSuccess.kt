package com.example.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.data.RegistrationViewModel
import com.example.project.navigation.defaultNavItems


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RedemptionSuccess(
    registrationViewModel: RegistrationViewModel,
    usedPoints: Int,
    newPoints: Int,
    uid: String,
    navController: NavHostController,
    onRedeemMoreClicked: () -> Unit
) {
    var uid = uid

    var bottomNavState by rememberSaveable { mutableIntStateOf(100) }

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Redemption Successful",
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display information about points used and remaining points
            Text("Redemption Successful!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Points Used: $usedPoints")
            Text("Remaining Points: $newPoints")

            // Button to navigate back to the Rewards screen
            Button(
                onClick = { onRedeemMoreClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Redeem More")
            }
        }
    }
}
