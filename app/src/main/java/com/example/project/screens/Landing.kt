package com.example.project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.components.LandingButtonComponent

@Composable
fun Landing(onLoginClicked: () -> Unit, onRegisterClicked: () -> Unit, navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFEFBF2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFFCF0D4))
                    .then(Modifier.padding(15.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(60.dp))

            LandingButtonComponent(value = "Login", iconName = Icons.Default.Lock, onButtonClicked = { navController.navigate("Login") }, isEnabled = true )


            Spacer(modifier = Modifier.height(16.dp))

            // Register Button
            LandingButtonComponent(value = "Register", iconName = Icons.Default.PersonAdd, onButtonClicked = { navController.navigate("Register") }, isEnabled = true )
        }
    }
}