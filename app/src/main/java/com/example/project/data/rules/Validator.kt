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
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val isValidEmail = email.matches(emailRegex.toRegex())
        return ValidationResult(
            (!email.isNullOrEmpty() && isValidEmail)
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