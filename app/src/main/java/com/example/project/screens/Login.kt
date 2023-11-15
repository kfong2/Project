package com.example.project.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.components.ButtonComponent
import com.example.project.components.DividerComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.InputComponent
import com.example.project.components.PasswordComponent
import com.example.project.components.TextButtonWithMessageComponent
import com.example.project.data.LoginUIEvent
import com.example.project.data.LoginViewModel


//@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavHostController) {
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
                HeadingComponent("Login")

                InputComponent(
                    labelValue = "Email",
                    iconName = Icons.Outlined.Email,
                    onTextSelected = { loginViewModel.onEvent(LoginUIEvent.EmailChanged(it)) },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                PasswordComponent(
                    labelValue = "Password",
                    iconName = Icons.Outlined.Lock,
                    onTextSelected = { loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it)) },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

//                AlignRightTextComponent(value = "Forgot Password?", nextScreen = "Register")

                ButtonComponent(
                    value = "Login",
                    onButtonClicked = { loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked) },
                    isEnabled = loginViewModel.allValidationsPassed.value // true = enable the button
                )

                DividerComponent()

                TextButtonWithMessageComponent(
                    message = "Don't have an account?",
                    action = { navController.navigate("Register") },
                    buttonText = "Register"
                )

           }

            if (loginViewModel.loginInProgress.value) {
                CircularProgressIndicator()
            }
        }
    }
}

