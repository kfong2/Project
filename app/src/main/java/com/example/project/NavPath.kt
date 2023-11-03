package com.example.project

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login"){


        composable(route = "Login"){
            LoginScreen(navController)
        }

        composable(route = "Registration"){
            RegistationScreen(navController)
        }

        composable(route = "Account"){
            Account(navController)
        }

        composable(route = "Dashboard"){
            Dashboard(navController)
        }
    }
}