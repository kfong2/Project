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
import com.example.project.screens.Account
import com.example.project.screens.Dashboard
import com.example.project.screens.Landing
import com.example.project.screens.Login
import com.example.project.screens.LoginFailure
import com.example.project.screens.MyRewards
import com.example.project.screens.ReceiptInput
import com.example.project.screens.ReceiptSubmitted
import com.example.project.screens.Redeem
import com.example.project.screens.RedemptionSuccess
import com.example.project.screens.RegFailure
import com.example.project.screens.Registration
import com.example.project.screens.Rewards
import com.example.project.screens.Transaction


@Composable
fun PointGrowRouter() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Landing") {

        composable(route = "Landing") {
            Landing(onLoginClicked = {}, onRegisterClicked = {}, navController)
        }

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

        composable(route = "Account/{uid}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val uid = arguments.getString("uid", "")
            Account(
                registrationViewModel = RegistrationViewModel(navController),
                navController,
                uid
            )
        }

        composable(route = "MyRewards/{uid}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val uid = arguments.getString("uid", "")
            MyRewards(registrationViewModel= RegistrationViewModel(navController), uid, navController)
        }

        composable(route = "Transaction/{uid}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val uid = arguments.getString("uid", "")
            Transaction(registrationViewModel= RegistrationViewModel(navController), uid, navController)
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

        composable(
            route = "ReceiptInput/{uid}",
            arguments = listOf(navArgument("uid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid")
            if (uid != null) {
                // Pass the uid parameter to the Rewards composable
                ReceiptInput(
                    registrationViewModel = RegistrationViewModel(navController),
                    uid,
                    navController
                )
            } else {
                // Stay in where the user is -> no action
            }
        }

        composable(route = "ReceiptSubmitted/{uid}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val uid = arguments.getString("uid", "")
            ReceiptSubmitted(
                registrationViewModel = RegistrationViewModel(navController),
                navController,
                uid,
                onSubmitMoreClicked = {
                    navController.navigate("ReceiptInput/$uid")
                }
            )
        }

        composable(
            route = "Rewards/{uid}",
            arguments = listOf(navArgument("uid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid")
            if (uid != null) {
                // Pass the uid parameter to the Rewards composable
                Rewards(
                    registrationViewModel = RegistrationViewModel(navController),
                    navController,
                    uid
                )
            } else {
                // Stay in where the user is -> no action
            }
        }

        composable(
            route = "Redeem/{rewardId}/{uid}",
            arguments = listOf(
                navArgument("rewardId") { type = NavType.StringType },
                navArgument("uid") { type = NavType.StringType }
            )

        ) { backStackEntry ->
            val rewardId = backStackEntry.arguments?.getString("rewardId")
            val uid = backStackEntry.arguments?.getString("uid")
            if (rewardId != null && uid != null) {
                Redeem(
                    registrationViewModel = RegistrationViewModel(navController),
                    navController = navController,
                    rewardId = rewardId,
                    uid = uid,
                    onBackClicked = { }
                )
            } else {
                // Stay in where the user is -> no action
            }
        }

        composable(
            route = "RedemptionSuccess/{usedPoints}/{newPoints}/{uid}",
            arguments = listOf(
                navArgument("usedPoints") { type = NavType.IntType },
                navArgument("newPoints") { type = NavType.IntType },
                navArgument("uid") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val usedPoints = backStackEntry.arguments?.getInt("usedPoints") ?: 0
            val newPoints = backStackEntry.arguments?.getInt("newPoints") ?: 0
            val uid = backStackEntry.arguments?.getString("uid") ?: ""

            RedemptionSuccess(
                registrationViewModel = RegistrationViewModel(navController),
                usedPoints = usedPoints,
                newPoints = newPoints,
                uid = uid,
                navController,
                onRedeemMoreClicked = {
                    navController.navigate("Rewards/$uid")
                }
            )
        }
    }
}

@Composable
fun NavigateTo(route: String, navController: NavHostController) {
    navController.navigate(route)
}