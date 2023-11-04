package com.example.project.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.project.data.rules.Validator

class LoginViewModel : ViewModel(){

    // consists of all the items in RegistrationUIState
    var registrationUIState = mutableStateOf(RegistrationUIState())

    private val TAG = LoginViewModel:: class.simpleName

    fun onEvent(event : UIEvent){
        validateDataWithRules()

        when (event){
            is UIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(firstName = event.firstName)

                printState()
            }

            is UIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(lastName = event.lastName)

                printState()
            }

            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(email = event.email)

                printState()
            }

            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(password = event.password)

                printState()
            }

            is UIEvent.RegisterButtonClicked -> {
                signUp()
            }

        }
    }

    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()

        validateDataWithRules()

    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            // pass the UI value for checking
            fName= registrationUIState.value.firstName
        )

        val lNameResult = Validator.validateLastName(
            // pass the UI value for checking
            lName= registrationUIState.value.lastName
        )

        val emailResult = Validator.validateEmail(
            // pass the UI value for checking
            email= registrationUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            // pass the UI value for checking
            password = registrationUIState.value.password
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "fNameRules = $fNameResult")
        Log.d(TAG, "lNameRules = $lNameResult")
        Log.d(TAG, "emailRules = $emailResult")
        Log.d(TAG, "passwordRules = $passwordResult")

        // update status
        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status

        )

    }

    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }
}