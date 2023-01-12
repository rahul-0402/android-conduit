package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.data.remote.TokenManager
import javax.inject.Inject

class GetUserAuthStateUseCase @Inject constructor(
    private val tokenManager: TokenManager
) {
    operator fun invoke(): Boolean {
        val token = tokenManager.getToken()
        return token.isNullOrEmpty().not()
    }
}