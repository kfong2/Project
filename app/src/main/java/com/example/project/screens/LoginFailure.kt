package com.example.project.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.components.DividerComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.TextButtonWithMessageComponent


@Composable
fun LoginFailure(navController: NavHostController){
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadingComponent("Login Failed")

                DividerComponent()

                TextButtonWithMessageComponent(
                    message = "Try Again?",
                    action = { navController.navigate("Login")},
                    buttonText = "Login"
                )

                TextButtonWithMessageComponent(
                    message = "Don't have an account?",
                    action = { navController.navigate("Register") },
                    buttonText = "Register"
                )
            }
        }
    }
}