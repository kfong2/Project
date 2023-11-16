package com.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project.data.LoginViewModel
import com.example.project.data.RegistrationViewModel
import com.example.project.screens.Dashboard
import com.example.project.screens.Login
import com.example.project.screens.LoginFailure
import com.example.project.screens.RegFailure
import com.example.project.screens.Registration
import com.example.project.screens.Rewards
import com.example.project.screens.Redeem


@Composable
fun PointGrowRouter() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login") {


        composable(route = "Login") {
            Login(loginViewModel = LoginViewModel(navController), navController)
        }

        composable(route = "Register") {
            Registration(
                registrationViewModel = RegistrationViewModel(navController),
                navController
            )
        }

        composable(route = "LoginFailure") {
            LoginFailure(navController)
        }

        composable(route = "RegFailure") {
            RegFailure(navController)
        }

        composable(route = "Dashboard/{uid}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val uid = arguments.getString("uid", "")
            Dashboard(
                registrationViewModel = RegistrationViewModel(navController),
                navController,
                uid
            )
        }

        composable(route = "Rewards") {
            Rewards(registrationViewModel = RegistrationViewModel(navController), navController)
        }

        composable(
            route = "Redeem/{rewardId}",
            arguments = listOf(navArgument("rewardId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rewardId = backStackEntry.arguments?.getString("rewardId") // Change from getInt to getString
//            val rewardId = backStackEntry.arguments?.getInt("rewardId")
            if (rewardId != null) {
                // Pass rewardId as a parameter to Redeem composable
                Redeem(
                    registrationViewModel = RegistrationViewModel(navController),
                    navController = navController,
                    rewardId = rewardId,
                    onBackClicked = {  }
//                            onBackClicked = { navController.popBackStack() }
                )
            } else {
                // Handle error case when rewardId is null
                // You might want to navigate to an error screen or go back to the previous screen
            }
        }
    }
}

@Composable
fun navigateTo(route: String, navController: NavHostController) {
    navController.navigate(route)
}