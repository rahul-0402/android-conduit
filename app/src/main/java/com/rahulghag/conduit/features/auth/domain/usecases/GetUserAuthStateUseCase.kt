package com.rahulghag.conduit.features.auth.domain.usecases

import com.rahulghag.conduit.features.auth.data.remote.TokenManager
import javax.inject.Inject

class GetUserAuthStateUseCase @Inject constructor(
    private val tokenManager: TokenManager
) {
    operator fun invoke(): Boolean {
        val token = tokenManager.getToken()
        return token.isNullOrEmpty().not()
    }
}