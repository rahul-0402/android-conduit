package com.rahulghag.conduit.data.remote

interface TokenManager {
    fun saveToken(token: String)

    fun getToken(): String?

    fun deleteToken()
}