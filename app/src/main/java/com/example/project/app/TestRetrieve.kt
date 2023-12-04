
package com.example.project.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project.functions.getUserDataFromFirebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestRetrieveUserDataScreen() {
    var uid by rememberSaveable { mutableStateOf("DiUm6QicOZUzqLavrkaVDJphD5o1") } // Replace with the actual user ID
    var firstName by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableStateOf(0) }

    LaunchedEffect("fetchUserData", uid) {
        getUserDataFromFirebase(uid) { userRecord ->
            userRecord?.let {
                firstName = it.firstName
                accumulatedPoints = it.accumulatedPoints
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Test Retrieve User Data Screen")
            Spacer(modifier = Modifier.height(16.dp))
            Text("First Name: $firstName")
            Text("Accumulated Points: $accumulatedPoints")
        }
    }
}
