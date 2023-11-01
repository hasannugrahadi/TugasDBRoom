package com.app.tugaskelompok

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user"
)

private val USER_EMAIL = stringPreferencesKey("user_email")
private val USER_PASSWORD = stringPreferencesKey("user_password")

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        getUserFromPreferencesStore().onEach { userSession ->
            val userEmail = userSession.userEmail
            val userPass = userSession.userPass

            Handler(Looper.getMainLooper()).postDelayed({
                if (userEmail != null && userPass != null) {
                    // User is already logged in, direct to the main activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // User is not logged in, direct to the motion page
                    // motion belum bisa di implement
                    val intent = Intent(this, MotionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 3000)
        }.launchIn(lifecycleScope)
    }

    fun clearUserPreferences() {
        lifecycleScope.launch {
            userPreferencesDataStore.edit { preferences ->
                preferences.remove(USER_EMAIL)
                preferences.remove(USER_PASSWORD)
            }
        }
    }


    private fun getUserFromPreferencesStore(): Flow<UserSession> = userPreferencesDataStore.data
        .map { preferences ->
            UserSession(
                userEmail = preferences[USER_EMAIL] ?: "",
                userPass = preferences[USER_PASSWORD] ?: ""
            )
        }

    data class UserSession(
        val userEmail: String,
        val userPass: String,
    )
}
