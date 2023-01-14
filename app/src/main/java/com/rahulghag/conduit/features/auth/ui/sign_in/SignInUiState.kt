package com.rahulghag.conduit.features.auth.ui.sign_in

import com.rahulghag.conduit.common.utils.UiMessage

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val isSignInSuccessful: Boolean = false
)
