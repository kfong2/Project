package com.example.project.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.project.screens.Dashboard
import com.example.project.screens.Login
import com.example.project.screens.Registration
import com.example.project.screens.RegFailure
import com.example.project.data.LoginViewModel
import com.example.project.navigation.PointGrowRouter
import com.example.project.navigation.Screen
import com.example.project.data.RegistrationViewModel
import com.example.project.screens.LoginFailure


@Composable
fun PointGrow(){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Crossfade(targetState = PointGrowRouter.currentScreen) {currentState ->
            when(currentState.value){
                is Screen.Login ->{
                    Login(loginViewModel = LoginViewModel())
                }

                is Screen.Registration ->{
                    Registration()
                }

                is Screen.Dashboard ->{
                    Dashboard(registrationViewModel = RegistrationViewModel())
                }

                is Screen.LoginFailure ->{
                    LoginFailure()
                }

                is Screen.RegFailure ->{
                    RegFailure()
                }

            }            
        }
    }
}