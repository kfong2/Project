package com.example.project

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegistationScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        var newTextValue by remember {
            mutableStateOf("")
        }

        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        Column (
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            TextField (value = newTextValue, onValueChange = {
                newTextValue = it
            },
                label = { Text(text = "Enter Your Username") },
                // maxLines = 1
                singleLine = true,
                modifier = Modifier.width(300.dp),
                placeholder = { Text(text = "user@mail.com") },
//                visualTransformation = PasswordVisualTransformation()
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        Toast.makeText(context, newTextValue, Toast.LENGTH_SHORT).show()
                    }
                    ) {
                        Icon(imageVector = Icons.Outlined.Send, contentDescription = "")
                    }
                }
            )

            OutlinedTextField (value = newTextValue, onValueChange = {
                newTextValue = it
            },
                label = { Text(text = "Enter Your Pasword") },
                singleLine = true,
                modifier = Modifier.width(300.dp),
                placeholder = { Text(text = "********") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        Toast.makeText(context, newTextValue, Toast.LENGTH_SHORT).show()
                    }
                    ) {
                        Icon(imageVector = Icons.Outlined.Send, contentDescription = "")
                    }
                },
                keyboardOptions = KeyboardOptions( // Set Keyboard
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        keyboardController?.hide() // keyboard will be hidden after clicked send
                        Toast.makeText(context, "send button pressed", Toast.LENGTH_LONG).show()
                        focusManager.clearFocus() // disabled the focus
                    }
                )
            )

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Login")
            }

        }
    }
}