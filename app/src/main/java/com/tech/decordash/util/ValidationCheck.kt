package com.tech.decordash.util

import android.util.Patterns
import java.util.regex.Pattern

class ValidationCheck {
    fun validateEmail(Email : String) : RegisterValidation
    {
        if(Email.isEmpty())
            return RegisterValidation.Failure("Email cannot be empty")
        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            return RegisterValidation.Failure("Invalid Email Format")
        else
            return RegisterValidation.Success
    }

    fun validatePassword(password: String): RegisterValidation {
        val minLength = 6
        val maxLength = 20

        // Check if password length is within the allowed range

        if(password.length < minLength)
            return RegisterValidation.Failure("Password is too small")

        if(password.length < minLength)
            return RegisterValidation.Failure("Password is too large")


        // Check if password contains at least one special character
        if (!password.any { !it.isLetterOrDigit() }) {
            return RegisterValidation.Failure("Password must contain a special character")
        }

        // All checks passed, password is valid
        return RegisterValidation.Success
    }


}