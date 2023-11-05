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
import com.example.project.components.DividerComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.TextButtonComponent
import com.example.project.navigation.PointGrowRouter
import com.example.project.navigation.Screen

@Composable
fun LoginFailure(){
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
                HeadingComponent("Login Failed?")


                DividerComponent()

                TextButtonComponent(
                    message = "Try Again",
                    action = { PointGrowRouter.navigateTo(Screen.Login)},
                    buttonText = "Login"
                )

                TextButtonComponent(
                    message = "Don't have an account?",
                    action = { PointGrowRouter.navigateTo(Screen.Registration) },
                    buttonText = "Register"
                )

            }

        }
    }

}