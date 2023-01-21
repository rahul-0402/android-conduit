package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.R
import com.rahulghag.conduit.domain.models.User
import com.rahulghag.conduit.domain.repositories.AuthRepository
import com.rahulghag.conduit.utils.Resource
import com.rahulghag.conduit.utils.UiMessage

class SignUpUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(username: String, email: String, password: String): Resource<User> {
        if (username.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.username_cannot_be_blank))
        }
        if (email.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.email_cannot_be_blank))
        }
        if (password.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.password_cannot_be_blank))
        }
        return authRepository.signUp(username = username, email = email, password = password)
    }
}