package com.rahulghag.conduit.features.auth.domain.usecases

import com.rahulghag.conduit.common.domain.models.User
import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.features.auth.domain.repositories.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        return authRepository.signIn(email = email, password = password)
    }
}