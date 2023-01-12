package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.domain.models.User
import com.rahulghag.conduit.domain.repositories.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        return authRepository.signIn(email = email, password = password)
    }
}