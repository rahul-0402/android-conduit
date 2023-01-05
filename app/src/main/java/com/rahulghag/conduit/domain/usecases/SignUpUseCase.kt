package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.models.User
import com.rahulghag.conduit.domain.repositories.AuthRepository
import com.rahulghag.conduit.utils.Resource

class SignUpUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String, username: String): Resource<User> {
        return authRepository.signUp(email = email, password = password, username = username)
    }
}