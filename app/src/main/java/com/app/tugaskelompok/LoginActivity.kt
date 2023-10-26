package com.app.tugaskelompok

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.tugaskelompok.database.UserDao
import com.app.tugaskelompok.database.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView

    private lateinit var database: UserRoomDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = UserRoomDatabase.getDatabase(applicationContext)
        userDao = database.UserDao()

        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        registerText = findViewById(R.id.openRegister)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val user = userDao.getUser(username, password)
                val email = userDao.getEmail(username)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        SharedPreferencesUtil.saveLoggedInUser(this@LoginActivity, username, email)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        registerText.makeLinks(
            Pair("Daftar sekarang!", View.OnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }))
    }

    private fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun updateDrawState(textPaint: TextPaint) {
                    // use this to change the link color
                    textPaint.color = textPaint.linkColor
                    // toggle below value to enable/disable
                    // the underline shown below the clickable text
                    textPaint.isUnderlineText = true
                }

                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
//      if(startIndexOfLink == -1) continue // todo if you want to verify your texts contains links text
            spannableString.setSpan(
                clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }
    object SharedPreferencesUtil {
        fun saveLoggedInUser(context: Context, username: String, email: String) {
            val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("userName", username)
            editor.putString("userEmail", email)
            editor.apply()
        }

        fun getLoggedInUser(context: Context): Pair<String?, String?> {
            val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val userName = sharedPreferences.getString("userName", null)
            val userEmail = sharedPreferences.getString("userEmail", null)

            return Pair(userName, userEmail)
        }
    }

}
