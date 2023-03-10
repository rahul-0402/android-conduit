package com.rahulghag.conduit.ui.auth.sign_up

sealed class SignUpUiEvent {
    data class EmailChanged(val email: String) : SignUpUiEvent()
    data class PasswordChanged(val password: String) : SignUpUiEvent()
    data class UsernameChanged(val username: String) : SignUpUiEvent()
    object SignUp : SignUpUiEvent()
}