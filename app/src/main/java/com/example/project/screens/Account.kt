package com.example.project.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.components.HeadingComponent

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Account(navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
//            .height(100.dp)
//            .aspectRatio(1.5f),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White)
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            var userFirst by remember { mutableStateOf("") }

            var userLast by remember { mutableStateOf("") }

            var userEmail by remember { mutableStateOf("") }

            var userPassword by remember { mutableStateOf("") }

//            val context = LocalContext.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeadingComponent(value = "Account")

                OutlinedTextField (value = userFirst, onValueChange = {
                    userFirst = it
                },
                    label = { Text(text = "First Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp, 10.dp, 25.dp, 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "First Name") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = "")
                    }
                )

                OutlinedTextField (value = userLast, onValueChange = {
                    userLast = it
                },
                    label = { Text(text = "Last Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp, 10.dp, 25.dp, 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "Last Name") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = "")
                    }
                )

                OutlinedTextField (value = userEmail, onValueChange = {
                    userEmail = it
                },
                    label = { Text(text = "Email") },
                    // maxLines = 1
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp, 10.dp, 25.dp, 10.dp),
                    placeholder = { Text(text = "user@mail.com") },
                    shape = RoundedCornerShape(10.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = "")
                    }
                )


                OutlinedTextField (value = userPassword, onValueChange = {
                    userPassword = it
                },
                    label = { Text(text = "Enter Your Password") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp, 10.dp, 25.dp, 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "********") },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
                    },
                    keyboardOptions = KeyboardOptions( // Set Keyboard
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            keyboardController?.hide() // keyboard will be hidden after clicked send
                            focusManager.clearFocus() // disabled the focus
                        }
                    )
                )

                Card(){
                    Row(){
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = "Update")
                        }

                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = "Cancel")
                        }
                    }


                }

            }
        }
    }
}