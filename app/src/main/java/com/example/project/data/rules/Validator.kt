package com.example.project.data.rules

object Validator {
    fun validateFirstName(fName : String) : ValidationResult{
        return ValidationResult(
            (!fName.isNullOrEmpty())
        )
    }

    fun validateLastName(lName : String) : ValidationResult{
        return ValidationResult(
            (!lName.isNullOrEmpty())
        )
    }

    fun validateEmail(email : String) : ValidationResult{
        return ValidationResult(
            (!email.isNullOrEmpty())
        )
    }

    fun validatePassword(password : String) : ValidationResult{
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 6)
        )
    }

}

data class ValidationResult(
    val status : Boolean = false
)