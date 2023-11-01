package com.app.tugaskelompok

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userData = LoginActivity.SharedPreferencesUtil.getLoggedInUser(this)
        val userName = userData.first
        val userEmail = userData.second

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            if (userName != null && userEmail != null) {
                // User is already logged in, direct to the main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // User is not logged in, direct to the motion page
                // motion belum bisa di implement
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}
