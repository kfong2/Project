package com.example.project.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.components.BottomNavigationBar
import com.example.project.components.TransactionInfo
import com.example.project.data.RegistrationViewModel
import com.example.project.data.TransactionData
import com.example.project.functions.getTransactionsForUser
import com.example.project.navigation.defaultNavItems


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Transaction(
    registrationViewModel: RegistrationViewModel,
    uid: String,
    navController: NavHostController
) {
    var uid = uid

    var transactionList by remember { mutableStateOf<List<TransactionData>>(emptyList()) }

    // Fetch transactions data from Firebase
    LaunchedEffect(uid) {
        getTransactionsForUser(uid) { transactionsData ->
            transactionList = transactionsData
        }
    }

    var bottomNavState by rememberSaveable { mutableIntStateOf(100) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Redemption History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            registrationViewModel.logout()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = defaultNavItems,
                selectedIndex = bottomNavState,
                onItemSelected = { index ->
                    bottomNavState = index
                    when (defaultNavItems[index].title) {
                        "Dashboard" -> navController.navigate("Dashboard/$uid")
                        "Rewards" -> navController.navigate("Rewards/$uid")
                        "Account" -> navController.navigate("Account/$uid")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 100.dp),
        ) {
            TransactionList(transactionList = transactionList)
        }
    }
}

@Composable
fun TransactionList(transactionList: List<TransactionData>) {
    LazyColumn(
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
    ) {
        items(transactionList) { transaction ->
            TransactionCard(transaction = transaction)
        }
    }
}

@Composable
fun TransactionCard(transaction: TransactionData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TransactionInfo("Reward Name", transaction.rewardName.toString())
            TransactionInfo("Used Points", transaction.requiredPoints.toString())
            TransactionInfo("Quantity", transaction.redeemedQuantity.toString())
            TransactionInfo("Date", transaction.transactionDate.toString())
        }
    }
}