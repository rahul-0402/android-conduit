package com.rahulghag.conduit.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rahulghag.conduit.data.repositories.PreferencesManagerImpl.Companion.PREFERENCES_NAME
import com.rahulghag.conduit.domain.repositories.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class PreferencesManagerImpl(
    context: Context
) : PreferencesManager {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val USERNAME = stringPreferencesKey("username")
    }

    override suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
        }
    }

    override fun getUsername(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val username = preferences[PreferencesKeys.USERNAME] ?: ""
                username
            }
    }

    companion object {
        const val PREFERENCES_NAME = "conduit_preferences"
    }
}