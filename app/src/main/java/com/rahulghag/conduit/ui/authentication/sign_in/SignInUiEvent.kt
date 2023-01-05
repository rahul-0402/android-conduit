package com.rahulghag.conduit.ui.authentication.sign_in

sealed class SignInUiEvent {
    data class EmailChanged(val email: String) : SignInUiEvent()
    data class PasswordChanged(val password: String) : SignInUiEvent()
    object SignIn : SignInUiEvent()
}