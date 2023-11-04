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
import androidx.navigation.NavHostController
import com.example.project.components.ButtonComponent
import com.example.project.components.DividerComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.InputComponent
import com.example.project.components.LoginOrRegComponent
import com.example.project.components.PasswordComponent
import com.example.project.components.TermsComponent

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
//            var userFirst by remember { mutableStateOf("") }
//
//            var userLast by remember { mutableStateOf("") }
//
//            var userEmail by remember { mutableStateOf("") }
//
//            var userPassword by remember { mutableStateOf("") }
//
////            val context = LocalContext.current
//            val keyboardController = LocalSoftwareKeyboardController.current
//            val focusManager = LocalFocusManager.current

            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeadingComponent("Register to PointGrow")

                InputComponent("First Name", Icons.Outlined.Person)

                InputComponent("Last Name", Icons.Outlined.Person)

                InputComponent("Email", Icons.Outlined.Email)

                PasswordComponent("Password", Icons.Outlined.Lock)

                TermsComponent("By signing up you accept our privacy policy and terms of use.")

                Spacer(modifier = Modifier.height(20.dp))
                
                ButtonComponent(navController, value = "Register", nextScreen = "Dashboard")

                DividerComponent()

                LoginOrRegComponent(navController, value = "Already have an account?", nextScreen = "Login")

            }
        }
    }
}