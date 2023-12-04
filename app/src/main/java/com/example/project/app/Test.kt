package com.example.project.app

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    onUpdatePoints: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Test Screen", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            // Test button for updating user data with exact figures
            TestButton(onUpdatePoints)
        }
    }
}

@Composable
fun TestButton(
    onUpdatePoints: () -> Unit
) {
    Button(
        onClick = {
            // Update user data with exact figures
            updateUserWithExactFigures(onUpdatePoints)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Update User Data with Exact Figures")
    }
}

private fun updateUserWithExactFigures(onUpdatePoints: () -> Unit) {
    val newPoints = 10000 // Replace with the exact number of points you want to set

    // Get a reference to the "users" collection in the Realtime Database
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    // Construct the path to the user's data using the hardcoded UID
    val uid = "DiUm6QicOZUzqLavrkaVDJphD5o1" // Replace with the actual user ID
    val userPath = "DiUm6QicOZUzqLavrkaVDJphD5o1" // Replace with the actual user ID
    val userRef = usersRef.child(userPath)

    // Update the "accumulatedPoints" field
    userRef.child("accumulatedPoints").setValue(newPoints)
        .addOnSuccessListener {
            Log.d(TAG, "User data updated successfully with exact figures")
            onUpdatePoints.invoke()
        }
        .addOnFailureListener { e ->
            Log.e(TAG, "Error updating user data with exact figures", e)
        }
}

private const val TAG = "TestScreen"
