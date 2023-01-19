package com.rahulghag.conduit.ui.auth.sign_in

import com.rahulghag.conduit.utils.UiMessage

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val isSignInSuccessful: Boolean = false
)
