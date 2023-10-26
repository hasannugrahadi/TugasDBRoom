package com.app.tugaskelompok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.tugaskelompok.database.User
import com.app.tugaskelompok.database.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var usergithubEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val database = UserRoomDatabase.getDatabase(applicationContext)
        val userDao = database.UserDao()

        usernameEditText = findViewById(R.id.register_username)
        passwordEditText = findViewById(R.id.register_password)
        usergithubEditText = findViewById(R.id.register_usergithub)
        emailEditText = findViewById(R.id.register_email)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val usergithub = usergithubEditText.text.toString()
            val email = emailEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && usergithub.isNotEmpty() && email.isNotEmpty()) {
                // Check if the username is already in use
                CoroutineScope(Dispatchers.IO).launch {
                    val existingUser = userDao.getUserByUsername(username)
                    if (existingUser == null) {
                        // User doesn't exist, proceed with registration
                        val newUser = User(userApp = username, password = password, userGithub = usergithub, email = email)
                        userDao.insert(newUser)
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Data berhasil disimpan, silahkan kembali", Toast.LENGTH_SHORT).show()
                        }
                        // Optionally, you can navigate to the login page or perform other actions.
                    } else {
                        // Username already in use, show an error message.
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Username telah digunakan", Toast.LENGTH_SHORT).show()
                        }
                        // You may want to use runOnUiThread or LiveData to update the UI.
                    }
                }
            } else {
                Toast.makeText(this, "Harap isi semua kolom yang ada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}