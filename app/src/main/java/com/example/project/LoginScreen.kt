package com.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project.components.AlignRightTextComponent
import com.example.project.components.ButtonComponent
import com.example.project.components.DividerComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.InputComponent
import com.example.project.components.LoginOrRegComponent
import com.example.project.components.PasswordComponent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.data.LoginViewModel
import com.example.project.data.UIEvent


//@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
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
                    onTextSelected = {  }
//                    onTextSelected = { loginViewModel.onEvent(UIEvent.EmailChanged(it)) },
//                    errorStatus = loginViewModel.registrationUIState.value.emailError
                )


                PasswordComponent(
                    labelValue = "Password",
                    iconName = Icons.Outlined.Lock,
                    onTextSelected = {  }
//                    onTextSelected = { loginViewModel.onEvent(UIEvent.PasswordChanged(it)) },
//                    errorStatus = loginViewModel.registrationUIState.value.passwordError
                )

//                AlignRightTextComponent(value = "Forgot Password?", nextScreen = "Register")

                ButtonComponent(
//                    navController,
                    value = "Login",
                    nextScreen = "Dashboard",
                    onButtonClicked = { loginViewModel.onEvent(UIEvent.RegisterButtonClicked) }
                )

                DividerComponent()

                LoginOrRegComponent(value = "Don't have an account?", nextScreen = "Register")
           }
        }
    }
}


@Preview (showBackground = true)
@Composable
fun AppPreview() {
}