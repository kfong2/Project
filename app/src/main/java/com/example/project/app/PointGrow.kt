package com.example.project.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.project.Dashboard
import com.example.project.LoginScreen
import com.example.project.RegistrationScreen
import com.example.project.navigation.PointGrowRouter
import com.example.project.navigation.Screen

@Composable
fun PointGrow(){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Crossfade(targetState = PointGrowRouter.currentScreen) {currentState ->
            when(currentState.value){
                is Screen.LoginScreen ->{
                    LoginScreen()
                }

                is Screen.RegistrationScreen ->{
                    RegistrationScreen()
                }

                is Screen.Dashboard ->{
                    Dashboard()
                }

            }            
        }
    }
}