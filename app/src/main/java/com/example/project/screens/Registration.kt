package com.example.project.screens

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
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.project.components.MessageComponent
import com.example.project.components.PasswordComponent
import com.example.project.components.TextButtonComponent
import com.example.project.data.RegistrationViewModel
import com.example.project.data.RegistrationUIEvent


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Registration(registrationViewModel: RegistrationViewModel, navController: NavHostController) {
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
                    onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvent.FirstNameChanged(it)) },
                    errorStatus = registrationViewModel.registrationUIState.value.firstNameError
                )

                InputComponent(
                    labelValue = "Last Name",
                    iconName = Icons.Outlined.Person,
                    onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvent.LastNameChanged(it)) },
                    errorStatus = registrationViewModel.registrationUIState.value.lastNameError
                )

                InputComponent(
                    labelValue = "Email",
                    iconName = Icons.Outlined.Email,
                    onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvent.EmailChanged(it)) },
                    errorStatus = registrationViewModel.registrationUIState.value.emailError
                )

                PasswordComponent(
                    labelValue = "Password",
                    iconName = Icons.Outlined.Lock,
                    onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvent.PasswordChanged(it)) },
                    errorStatus = registrationViewModel.registrationUIState.value.passwordError
                )

                MessageComponent("By signing up you accept our privacy policy and terms of use.")

                Spacer(modifier = Modifier.height(20.dp))
                
                ButtonComponent(
                    value = "Register",
                    onButtonClicked = { registrationViewModel.onEvent(RegistrationUIEvent.RegisterButtonClicked) },
                    isEnabled = registrationViewModel.allValidationsPassed.value // true = enable the button
                )

                DividerComponent()

                TextButtonComponent(
                    message = "Already have an account?",
                    action = { navController.navigate("Login")},
                    buttonText = "Login"
                )

            }

            if (registrationViewModel.signUpInProgress.value) {
                CircularProgressIndicator()
            }
        }
    }
}