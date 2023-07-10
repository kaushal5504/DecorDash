package com.tech.decordash.util

sealed class RegisterValidation(){
    object Success : RegisterValidation()
    data class Failure(val message : String) :RegisterValidation()


}

data class RegisterFieldState(
    val email :RegisterValidation,
    val password : RegisterValidation
)