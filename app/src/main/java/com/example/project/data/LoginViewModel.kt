package com.example.project.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.project.data.rules.Validator
import com.example.project.navigation.PointGrowRouter
import com.example.project.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel:: class.simpleName

    // consists of all the items in LoginUIState
    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    fun onEvent(event : LoginUIEvent){
        when(event){
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
                validateLoginUIDataWithRules()
//                printState()
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
                validateLoginUIDataWithRules()
//                printState()
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }

        }
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            // pass the UI value for checking
            email= loginUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            // pass the UI value for checking
            password = loginUIState.value.password
        )

        // update status
        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status,
        )

        // check if all validations passed
        allValidationsPassed.value = emailResult.status && passwordResult.status

    }


    private fun login() {
        loginInProgress.value = true

        val email = loginUIState.value.email
        val password = loginUIState.value.password
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_loginSuccess")
                Log.d(TAG, "${it.isSuccessful}")

                if (it.isSuccessful){
                    PointGrowRouter.navigateTo(Screen.Dashboard)
                    loginInProgress.value = false
                    allValidationsPassed.value = false // Reset the button to disabled
                }
            }
            .addOnFailureListener {
                PointGrowRouter.navigateTo(Screen.LoginFailure)
                Log.d(TAG, "Inside_loginFailure")
                Log.d(TAG, "${it.localizedMessage}")
                loginInProgress.value = false
            }
    }
}


