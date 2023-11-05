package com.example.project.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.project.data.rules.Validator
import com.example.project.navigation.PointGrowRouter
import com.example.project.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel(){

    // consists of all the items in RegistrationUIState
    var registrationUIState = mutableStateOf(RegistrationUIState())

    private val TAG = LoginViewModel:: class.simpleName

    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    fun onEvent(event : UIEvent){
        when (event){
            is UIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(firstName = event.firstName)
                validateDataWithRules()
                printState()
            }

            is UIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(lastName = event.lastName)
                validateDataWithRules()
                printState()
            }

            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(email = event.email)
                validateDataWithRules()
                printState()
            }

            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(password = event.password)
                validateDataWithRules()
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

        createUserInFireBase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )

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
            passwordError = passwordResult.status,
        )

        // check if all validations passed
        allValidationsPassed.value = fNameResult.status && lNameResult.status && emailResult.status && passwordResult.status

    }

    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }


    // will be called when using signUp()
    private fun createUserInFireBase(email: String, password: String){
        signUpInProgress.value = true

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")

                if(it.isSuccessful){
                    PointGrowRouter.navigateTo(Screen.Dashboard)
                    signUpInProgress.value = false
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")
            }

    }

}