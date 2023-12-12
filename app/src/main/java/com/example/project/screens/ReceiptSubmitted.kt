package com.example.project.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.data.RegistrationViewModel
import com.example.project.navigation.defaultNavItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptSubmitted(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    uid: String,
    onSubmitMoreClicked: () -> Unit
) {
    var uid = uid

    var bottomNavState by rememberSaveable { mutableIntStateOf(100) }

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Receipt Submission",
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
    ) {contentPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display information about points used and remaining points
            Text(
                "Receipt has been successfully submitted!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Button to navigate back to the Rewards screen
            Button(
                onClick = { onSubmitMoreClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Submit another one")
            }
        }
    }
}