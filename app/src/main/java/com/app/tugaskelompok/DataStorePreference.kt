package com.app.tugaskelompok
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "user_preferences")

class DataStorePreference(context: Context) {

    private val dataStore = context.dataStore

    private val usersID = stringPreferencesKey("uid")
    private val loggedIn = booleanPreferencesKey("logged_In")

    suspend fun saveUserPreferences(username: String, status: Boolean) {
        dataStore.edit { preferences ->
            preferences[usersID] = username
            preferences[loggedIn] = status
        }
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .map { preferences ->
            val username = preferences[usersID] ?: ""
            val isDarkMode = preferences[loggedIn] ?: false
            UserPreferences(username, isDarkMode)
        }

    suspend fun getUserid(): String {
        val preferences = dataStore.data.first()
        return preferences[usersID] ?: ""
    }

    suspend fun getLoggedStatus(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[loggedIn] ?: false
    }

    suspend fun eraseUserPreferences() {
        dataStore.edit { preferences ->
            preferences.remove(usersID)
            preferences.remove(loggedIn)
        }
    }
}

data class UserPreferences(
    val usersID: String,
    val loggedIn: Boolean
)
