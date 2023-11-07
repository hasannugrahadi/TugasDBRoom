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

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val preferenceDataStore = DataStore(this)
        val userEmail = preferenceDataStore.getValue1()
        val userUID = preferenceDataStore.getValue2()

        Handler(Looper.getMainLooper()).postDelayed({
                if (userEmail != null && userUID != null) {
                    // User is already logged in, direct to the main activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // User is not logged in, direct to the motion page
                    val intent = Intent(this, MotionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 3000)
    }
}

