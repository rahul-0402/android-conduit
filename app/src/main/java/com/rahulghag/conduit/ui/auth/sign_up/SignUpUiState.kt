package com.rahulghag.conduit.ui.auth.sign_up

import com.rahulghag.conduit.utils.UiMessage

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val isSignUpSuccessful: Boolean = false
)
