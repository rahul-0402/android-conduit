package com.rahulghag.conduit.features.auth.ui.sign_up

import com.rahulghag.conduit.common.utils.UiMessage

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val isSignUpSuccessful: Boolean = false
)
