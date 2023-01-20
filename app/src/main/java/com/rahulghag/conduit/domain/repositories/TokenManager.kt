package com.rahulghag.conduit.domain.repositories

interface TokenManager {
    fun saveToken(token: String)

    fun getToken(): String?

    fun deleteToken()
}