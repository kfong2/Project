package com.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.Account
import com.example.project.Dashboard
import com.example.project.LoginScreen
import com.example.project.RegistrationScreen

@Composable
fun Nav(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login"){


        composable(route = "Login"){
            LoginScreen(navController)
        }

        composable(route = "Register"){
            RegistrationScreen(navController)
        }

        composable(route = "Account"){
            Account(navController)
        }

        composable(route = "Dashboard"){
            Dashboard(navController)
        }
    }
}