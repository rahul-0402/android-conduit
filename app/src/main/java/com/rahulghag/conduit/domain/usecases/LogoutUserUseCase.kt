package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.repositories.PreferencesManager
import com.rahulghag.conduit.domain.repositories.TokenManager

class LogoutUserUseCase(
    private val preferencesManager: PreferencesManager,
    private val tokenManager: TokenManager
) {
    suspend operator fun invoke() {
        preferencesManager.saveUsername(username = "")
        tokenManager.deleteToken()
    }
}