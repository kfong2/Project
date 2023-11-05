package com.example.project.data

sealed class RegistrationUIEvent{
    data class FirstNameChanged(val firstName : String) : RegistrationUIEvent()
    data class LastNameChanged(val lastName : String) : RegistrationUIEvent()
    data class EmailChanged(val email : String) : RegistrationUIEvent()
    data class PasswordChanged(val password : String) : RegistrationUIEvent()
    object RegisterButtonClicked : RegistrationUIEvent()
}
