package com.example.project

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class NavItemState(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector
//    val hasBadge : Boolean,
//    val badgeNum : Int
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Dashboard() {
    val items = listOf(
        NavItemState(
            title = "Dashboard",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
//            hasBadge = false
////            badgeNum = 0
        ),
        NavItemState(
            title = "Register",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email
//            hasBadge = true,
//            badgeNum = 10
        ),
        NavItemState(
            title = "Account",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face
//            hasBadge = true,
//            badgeNum = 0
        )
    )
    var bottomNavState by rememberSaveable { mutableStateOf(0)}

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
//                    contentAlignment = Alignment.Center
                ){
                    Text(text = "PointGrow")

                }
            },
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },

//                actions = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        BadgedBox(badge = {
//                            Badge(
//                                modifier = Modifier.size(10.dp)
//                            ) {}
//                        }){
//                            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Fav Icon")
//                        }
//                    }
//
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "ShoppingCard Icon")
//                    }
//                },
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.secondary.copy (alpha = .7f)
//                )

            )

        },
        bottomBar = {
            NavigationBar (
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp)),
                containerColor = MaterialTheme.colorScheme.secondary.copy (alpha = .5f)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = bottomNavState == index,
//                        onClick = { bottomNavState = index, navController.navigate("Account")},
                        onClick = { },

                        icon = {
//                            BadgedBox(badge = {
//                                if (item.hasBadge) Badge {}
//                                if (item.badgeNum != 0) Badge {
//                                    Text(text = item.badgeNum.toString())
//                                }
//                            }
//                            ) {
                            Icon(
                                imageVector = if (bottomNavState == index) item.selectedIcon
                                else item.unselectedIcon,
                                contentDescription = item.title
                            )
//                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = Color(0xFF552A27),
                            selectedTextColor = Color(0xFF131F0D)
//                            indicatorColor = Color(0xFFF1F1EA)
                        )
                    )
                }
            }

        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = { /*TODO*/ }) {
//                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
//            }
//        }
    ){ contentPadding ->

        Column (
            modifier = Modifier.padding(contentPadding)
        ) {
            Text(text = "Content")
        }
    }

//    Card(
//        modifier = Modifier
//            .padding(5.dp)
//            .fillMaxSize()
////            .height(100.dp)
////            .aspectRatio(1.5f),
////        colors = CardDefaults.cardColors(
////            containerColor = Color.White)
//    ){
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ){
//            var userEmail by remember { mutableStateOf("") }
////            var isValidEmail by remember { mutableStateOf(true) }
////            val inValidEmailMessage = "Please enter a valid email."
//
//            var userPassword by remember { mutableStateOf("") }
//
//
//
////            val context = LocalContext.current
//            val keyboardController = LocalSoftwareKeyboardController.current
//            val focusManager = LocalFocusManager.current
//
//            Column (
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//                Text(
//                    text = "Welcome back, XXX",
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                OutlinedTextField (value = userEmail, onValueChange = {
//                    userEmail = it
//                },
//                    label = { Text(text = "Email") },
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(25.dp),
//                    shape = RoundedCornerShape(10.dp),
//                    placeholder = { Text(text = "user@mail.com") },
//                    leadingIcon = {
//                        Icon(imageVector = Icons.Outlined.Email, contentDescription = "")
//                    }
//                )
//
////                isValidEmail = isValidEmail(userEmail)
////
////                if (!isValidEmail) {
////                    Text(
////                        text = "Invalid email address",
////                        color = MaterialTheme.colorScheme.error
////                    )
////                }
//
//
//
//                OutlinedTextField (value = userPassword, onValueChange = {
//                    userPassword = it
//                },
//                    label = { Text(text = "Enter Your Password") },
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(25.dp, 0.dp, 25.dp, 0.dp),
//                    shape = RoundedCornerShape(10.dp),
//                    placeholder = { Text(text = "********") },
//                    visualTransformation = PasswordVisualTransformation(),
//                    leadingIcon = {
//                        Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
//                    },
//                    keyboardOptions = KeyboardOptions( // Set Keyboard
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Send
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onSend = {
//                            keyboardController?.hide() // keyboard will be hidden after clicked send
////                            Toast.makeText(context, "send button pressed", Toast.LENGTH_LONG).show()
//                            focusManager.clearFocus() // disabled the focus
//                        }
//                    )
//                )
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(0.dp, 0.dp, 25.dp, 0.dp)
//                        .height(30.dp),
//                    contentAlignment = Alignment.CenterEnd
//                ){
//                    TextButton(
//                        onClick = { navController.navigate("") }
//                    ) {
//                        Text(
//                            text = "Forgot Password?"
//                            , fontSize = 10.sp
//                            , fontStyle = FontStyle.Italic
//                        )
//                    }
//                }
//
//
//                Button(
//                    onClick = {/*TODO*/ },
//                    shape = RoundedCornerShape(10.dp)
//                ) {
//                    Text(text = "Login")
//                }
//
//                Spacer(modifier = Modifier.size(35.dp))
//
//                Text(text = "Don't have an account?")
//
//                TextButton(
//                    onClick = { navController.navigate("Registration") }
//                ) {
//                    Text(text = "Register")
//                }
//            }
//        }
//    }
}