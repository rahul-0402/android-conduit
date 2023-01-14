package com.rahulghag.conduit.features.auth.data.remote

interface TokenManager {
    fun saveToken(token: String)

    fun getToken(): String?

    fun deleteToken()
}