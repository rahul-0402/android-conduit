package com.rahulghag.conduit.domain.repositories

import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.domain.models.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Resource<User>
    suspend fun signUp(email: String, password: String, username: String): Resource<User>
}