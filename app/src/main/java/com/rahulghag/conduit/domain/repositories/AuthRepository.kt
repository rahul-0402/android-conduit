package com.rahulghag.conduit.domain.repositories

import com.rahulghag.conduit.domain.models.User
import com.rahulghag.conduit.utils.Resource

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Resource<User>
    suspend fun signUp(username: String, email: String, password: String): Resource<User>
}