package com.example.project.screens

import android.app.DatePickerDialog
import android.content.ContentValues
import android.util.Log
import android.os.Button
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.res.stringResource
import com.example.project.R
import com.example.project.components.AccountGreeting
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.components.LandingButtonComponent
import com.example.project.components.ProfileInfoItem
import com.example.project.data.RegistrationViewModel
import com.example.project.functions.getUserDataFromFirebase
import com.example.project.navigation.defaultNavItems
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    uid: String
) {
    var uid = uid
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var registrationDate by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableIntStateOf(0) }

    // Fetch user data from Firebase
    LaunchedEffect("fetchUserData", uid) {
        getUserDataFromFirebase(uid) { fetchedUserRecord ->
            fetchedUserRecord?.let {
                firstName = it.firstName
                lastName = it.lastName
                email = it.email
                birthday = it.birthday
                registrationDate = it.registrationDate
                accumulatedPoints = it.accumulatedPoints
                Log.d(
                    ContentValues.TAG,
                    "Fetched user data: firstName=$firstName, accumulatedPoints=$accumulatedPoints, uid: $uid"
                )
            }
        }
    }

    var bottomNavState by rememberSaveable { mutableIntStateOf(2) }
    var context = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }
    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )


    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Account",
                logoutButtonClicked = {
                    registrationViewModel.logout()
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = defaultNavItems,
                selectedIndex = bottomNavState,
                onItemSelected = { index ->
                    bottomNavState = index
/*                    when (defaultNavItems[index].title) {
                        "Dashboard" -> navController.navigate("Dashboard/$uid")
                        "Rewards" -> navController.navigate("Rewards/$uid")
                        "Account" -> navController.navigate("Account/$uid")
                    }*/

                    when(defaultNavItems[index].title){
                        context.resources.getString(R.string.nav_dashboard) -> navController.navigate(context.resources.getString(R.string.nav_dashboard) + "/$uid")
                        context.resources.getString(R.string.nav_rewards) -> navController.navigate(context.resources.getString(R.string.nav_rewards) + "/$uid")
                        context.resources.getString(R.string.nav_account) -> navController.navigate(context.resources.getString(R.string.nav_account) + "/$uid")
                    }


                }
            )
        }
    ) { contentPadding ->

        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(contentPadding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        AccountGreeting(firstName = firstName, points = accumulatedPoints)

                        Spacer(modifier = Modifier.height(20.dp))

                        // Display other user data
                        ProfileInfoItem("First Name", firstName)
                        ProfileInfoItem("Last Name", lastName)
                        ProfileInfoItem("Email", email)
                        ProfileInfoItem("Birthday", birthday)
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                            // Creating a button that on
                            // click displays/shows the DatePickerDialog
                            Button(onClick = {
                                mDatePickerDialog.show()
                            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
                                Text(text = "Open Date Picker", color = Color.White)
                            }

                            // Adding a space of 100dp height
                            Spacer(modifier = Modifier.size(100.dp))

                            // Displaying the mDate value in the Text
                            Text(text = "Selected Date: ${mDate.value}", fontSize = 30.sp, textAlign = TextAlign.Center)
                        }
                        ProfileInfoItem("Registration Date", registrationDate)
                    }
                }

                // Button: Navigate to MyRewards Screen
                LandingButtonComponent(
                    value = "My Rewards",
                    iconName = Icons.Default.List,
                    onButtonClicked = { navController.navigate("MyRewards/$uid") },
                    isEnabled = true
                )

                // Button: Navigate to Redemption History
                LandingButtonComponent(
                    value = "View Redemption History",
                    iconName = Icons.Default.List,
                    onButtonClicked = { navController.navigate("Transaction/$uid") },
                    isEnabled = true
                )
            }
        }
    }
}

@Composable
fun Button(onClick: () -> Unit, colors: Any, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}
