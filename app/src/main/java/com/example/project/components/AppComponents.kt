@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.project.components

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.Discount
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.example.project.data.RewardData


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

@Composable
fun SubheadingComponent(value : String){
    Text(
        text = value,
        modifier = Modifier
//            .fillMaxWidth()
            .heightIn(min = 30.dp)
            .padding(start = 30.dp, top = 3.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InputComponent(
    labelValue: String,
    iconName: ImageVector,
    onTextSelected : (String) -> Unit,
    errorStatus : Boolean = false
){
    var input by remember { mutableStateOf("") }

    OutlinedTextField (
        value = input,
        onValueChange = {
            input = it
            onTextSelected(it)
        },
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
            imeAction = ImeAction.Next
        ),
        isError = !errorStatus
    )
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PasswordComponent(
    labelValue: String,
    iconName: ImageVector,
    onTextSelected : (String) -> Unit,
    errorStatus : Boolean = false
){
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) } // password is not visible

    val localFocusManager = LocalFocusManager.current

    OutlinedTextField (
        value = password,
        onValueChange = {
            password = it
            onTextSelected(it)
        },
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
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },

        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        keyboardOptions = KeyboardOptions( // Set Keyboard
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions{
            localFocusManager.clearFocus()
        },
        isError = !errorStatus
    )
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlignRightTextComponent(value: String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 25.dp, 0.dp)
            .height(30.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        TextButton(
            onClick = { }
        ) {
            Text(
                text = value
                , fontSize = 10.sp
                , fontStyle = FontStyle.Italic
            )
        }
    }
}


@Composable
fun MessageComponent(value : String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 5.dp)
            .padding(40.dp, 10.dp, 40.dp, 5.dp),
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )
}


@Composable
fun ButtonComponent(
    value: String,
    onButtonClicked : () -> Unit,
    isEnabled : Boolean = false
){
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled
    ) {
        Text(text = value)
    }
}

@Composable
fun ButtonWithIconAndMessageComponent(
    value: String,
    iconName: ImageVector,
    points: String,
    message: String,
    onButtonClicked : () -> Unit,
    isEnabled : Boolean = false
){
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier.fillMaxWidth(0.5f),
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = value,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.tertiary
                )
            Icon(
                imageVector = iconName,
                contentDescription = "",
                modifier = Modifier.size(50.dp),
                tint = Color(0xFFBB7E7A)
            )
            Text(
                text = points,
                fontSize = 15.sp)
            Text(text = "Points")
            Text(
                text = message,
                textAlign = TextAlign.Center
            )
        }

    }
}



@Composable
fun DividerComponent(){
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 60.dp, 30.dp, 15.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.tertiary
    )
}


@Composable
fun TextButtonComponent(message : String, action : () -> Unit, buttonText : String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = message,
            fontSize = 14.sp
        )

        TextButton(
            onClick = action
        ) {
            Text(
                text = buttonText,
                fontSize = 14.sp
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(toolbarTitle : String, logoutButtonClicked : () -> Unit){
    TopAppBar(
        title = {
            Text(text = toolbarTitle)
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
//                tint = Color.White,

//                modifier = Modifier.clickable {  }
            )
        },
        actions = {
            IconButton(
                onClick = {
                    logoutButtonClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout"
                )
            }

        }
    )
}


@Composable
fun WelcomeBackComponent(firstName: String, points: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Internal padding for the content inside the card
        ) {
            HeadingComponent("Welcome back,")
            HeadingComponent(firstName) // Display the user's first name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SubheadingComponent(value = "$points")
                Text(text = " Points")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyRowComponent(rewardsList: List<RewardData>, onItemClick: (RewardData) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        content = {
            items(rewardsList) { reward ->
                // Use RewardItem directly here
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = { onItemClick(reward) }),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .height(180.dp)
                            .width(180.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Outlined.Discount, contentDescription = "", modifier = Modifier.size(48.dp))
                        Text(text = reward.rewardName, fontSize = 18.sp, fontWeight = FontWeight.Bold, softWrap = true, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Required Points: ${reward.requiredPoints}", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Quantity: ${reward.quantity}", fontSize = 14.sp)
                    }
                }
            }
        }
    )
}
