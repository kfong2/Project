package com.example.project.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen(){

    object LoginScreen : Screen ()
    object RegistrationScreen : Screen()

    object Dashboard : Screen()

}

object PointGrowRouter {
    val currentScreen : MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}