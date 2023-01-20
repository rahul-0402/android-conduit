package com.rahulghag.conduit.domain.repositories

import kotlinx.coroutines.flow.Flow


interface PreferencesManager {
    suspend fun saveUsername(username: String)

    fun getUsername(): Flow<String>
}