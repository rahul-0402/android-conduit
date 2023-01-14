package com.rahulghag.conduit.features.auth.domain.repositories

import com.rahulghag.conduit.common.domain.models.User
import com.rahulghag.conduit.common.utils.Resource

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Resource<User>
    suspend fun signUp(email: String, password: String, username: String): Resource<User>
}