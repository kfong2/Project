package com.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.data.LoginViewModel
import com.example.project.data.RegistrationViewModel
import com.example.project.screens.Dashboard
import com.example.project.screens.Login
import com.example.project.screens.LoginFailure
import com.example.project.screens.RegFailure
import com.example.project.screens.Registration
import com.example.project.screens.Rewards

@Composable
fun PointGrowRouter(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login"){


        composable(route = "Login"){
            Login(loginViewModel = LoginViewModel(navController), navController)
        }

        composable(route = "Register"){
            Registration(registrationViewModel = RegistrationViewModel(navController), navController)
        }

        composable(route = "LoginFailure"){
            LoginFailure(navController)
        }

        composable(route = "RegFailure"){
            RegFailure(navController)
        }

        composable(route = "Dashboard/{uid}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val uid = arguments.getString("uid", "")
            Dashboard(registrationViewModel = RegistrationViewModel(navController), navController, uid)
        }

        composable(route = "Rewards") {
            Rewards(registrationViewModel = RegistrationViewModel(navController), navController)
        }
    }
}

@Composable
fun navigateTo(route: String, navController: NavHostController) {
    navController.navigate(route)
}