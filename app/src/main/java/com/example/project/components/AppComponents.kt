package com.example.project.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController



@Composable
fun HeadingComponent(value : String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp)
            .padding(start = 30.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InputComponent(labelValue: String, iconName: ImageVector){

    var input by remember { mutableStateOf("") }

    OutlinedTextField (
        value = input,
        onValueChange = { input = it},
        label = { Text(text = labelValue) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy (alpha = .5f),
            cursorColor = MaterialTheme.colorScheme.primary.copy (alpha = .7f)
        ),
        leadingIcon = {
            Icon(imageVector = iconName, contentDescription = "")
        },
        keyboardOptions = KeyboardOptions( // Set Keyboard
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PasswordComponent(labelValue: String, iconName: ImageVector){

    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) } // password is not visible

    OutlinedTextField (
        value = password,
        onValueChange = { password = it},
        label = { Text(text = labelValue) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy (alpha = .5f),
            cursorColor = MaterialTheme.colorScheme.primary.copy (alpha = .7f)
        ),
        leadingIcon = {
            Icon(imageVector = iconName, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible){
                Icons.Filled.Visibility
            } else{
                Icons.Filled.VisibilityOff
            }

            var description = if(passwordVisible){
                "Hide Password"
            } else{
                "Show Password"
            }

            IconButton(onClick = { passwordVisible = !passwordVisible } ){
                Icon(imageVector = iconImage, contentDescription = "description")
            }
        },

        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        keyboardOptions = KeyboardOptions( // Set Keyboard
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Send
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordComponent(navController: NavHostController){
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
}

@Composable
fun TermsComponent(value : String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 5.dp)
            .padding(30.dp, 10.dp, 30.dp, 5.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )
}


@Composable
fun ButtonComponent(navController: NavHostController, value: String, nextScreen : String){
    Button(
        onClick = { navController.navigate(nextScreen) },
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = value)
    }
}


@Composable
fun LoginOrRegComponent(navController: NavHostController, value : String, nextScreen : String){
    Row(
        modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = value,
            fontSize = 14.sp
        )

        TextButton(
            onClick = { navController.navigate(nextScreen) }
        ) {
            Text(
                text = nextScreen,
                fontSize = 14.sp
            )
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckboxComponent(value : String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
//            .height(55.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        var checkedState by remember { mutableStateOf(false)}

        Checkbox(
            checked = checkedState,
            onCheckedChange = {
                checkedState != checkedState
            })
//        SmallTextComponent(value)
    }
}