package com.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.components.ButtonComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.InputComponent
import com.example.project.components.LoginOrRegComponent
import com.example.project.components.PasswordComponent


//@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
//            var userEmail by remember { mutableStateOf("") }
////            var isValidEmail by remember { mutableStateOf(true) }
////            val inValidEmailMessage = "Please enter a valid email."
//
//            var userPassword by remember { mutableStateOf("") }
//
////            val context = LocalContext.current
//            val keyboardController = LocalSoftwareKeyboardController.current
//            val focusManager = LocalFocusManager.current

            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadingComponent("Login")

                InputComponent("Email", Icons.Outlined.Email)

                PasswordComponent("Password", Icons.Outlined.Lock)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 25.dp, 0.dp)
                        .height(30.dp),
                    contentAlignment = Alignment.CenterEnd
                ){
                    TextButton(
                        onClick = { navController.navigate("") }
                    ) {
                        Text(
                            text = "Forgot Password?"
                            , fontSize = 10.sp
                            , fontStyle = FontStyle.Italic
                        )
                    }
                }

                ButtonComponent(navController, value = "Login", nextScreen = "Dashboard")

                Divider(
                    modifier = Modifier.fillMaxWidth().padding(30.dp, 60.dp, 30.dp, 15.dp),
                    thickness = 1.dp
                )

                LoginOrRegComponent(navController, value = "Don't have an account?", nextScreen = "Register")
           }
        }
    }
}


@Preview (showBackground = true)
@Composable
fun AppPreview() {
}