package com.example.project.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen(){

    object Login : Screen ()
    object Registration : Screen()
    object Dashboard : Screen()
    object LoginFailure : Screen()
    object RegFailure : Screen()

}

object PointGrowRouter {
    val currentScreen : MutableState<Screen> = mutableStateOf(Screen.LoginFailure)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}