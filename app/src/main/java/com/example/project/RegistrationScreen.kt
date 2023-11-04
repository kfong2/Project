package com.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project.components.ButtonComponent
import com.example.project.components.DividerComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.InputComponent
import com.example.project.components.LoginOrRegComponent
import com.example.project.components.PasswordComponent
import com.example.project.components.TermsComponent
import com.example.project.data.LoginViewModel
import com.example.project.data.UIEvent

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
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
                HeadingComponent("Register to PointGrow")

                InputComponent(
                    labelValue = "First Name",
                    iconName = Icons.Outlined.Person,
                    onTextSelected = { loginViewModel.onEvent(UIEvent.FirstNameChanged(it)) },
                    errorStatus = loginViewModel.registrationUIState.value.firstNameError
                )

                InputComponent(
                    labelValue = "Last Name",
                    iconName = Icons.Outlined.Person,
                    onTextSelected = { loginViewModel.onEvent(UIEvent.LastNameChanged(it)) },
                    errorStatus = loginViewModel.registrationUIState.value.lastNameError
                )

                InputComponent(
                    labelValue = "Email",
                    iconName = Icons.Outlined.Email,
                    onTextSelected = { loginViewModel.onEvent(UIEvent.EmailChanged(it)) },
                    errorStatus = loginViewModel.registrationUIState.value.emailError
                )

                PasswordComponent(
                    labelValue = "Password",
                    iconName = Icons.Outlined.Lock,
                    onTextSelected = { loginViewModel.onEvent(UIEvent.PasswordChanged(it)) },
                    errorStatus = loginViewModel.registrationUIState.value.passwordError
                )

                TermsComponent("By signing up you accept our privacy policy and terms of use.")

                Spacer(modifier = Modifier.height(20.dp))
                
                ButtonComponent(
                    navController,
                    value = "Register",
                    nextScreen = "Dashboard",
                    onButtonClicked = { loginViewModel.onEvent(UIEvent.RegisterButtonClicked) }
                )

                DividerComponent()

                LoginOrRegComponent(navController, value = "Already have an account?", nextScreen = "Login")

            }
        }
    }
}