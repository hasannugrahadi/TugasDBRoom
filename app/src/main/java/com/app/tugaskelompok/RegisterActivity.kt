package com.app.tugaskelompok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var usergithubEditText: EditText
    private lateinit var usernimEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var backButton: ImageButton

    private val mAuth = FirebaseAuth.getInstance()

    private val database = FirebaseDatabase.getInstance()
    private val usersReference = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameEditText = findViewById(R.id.register_username)
        passwordEditText = findViewById(R.id.register_password)
        usergithubEditText = findViewById(R.id.register_usergithub)
        usernimEditText = findViewById(R.id.register_nim)
        emailEditText = findViewById(R.id.register_email)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val usergithub = usergithubEditText.text.toString()
            val nim = usernimEditText.text.toString()
            val email = emailEditText.text.toString()

            val user = User(username, usergithub, nim)

            if (username.isNotEmpty() && password.isNotEmpty() && usergithub.isNotEmpty() && email.isNotEmpty() && nim.isNotEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                            val userId = mAuth.currentUser?.uid
                            if (userId != null) {
                                usersReference.child(userId).setValue(user)
                            }
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Registrasi Gagal: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Harap isi semua kolom yang ada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class User(
    val username: String,
    val usergithub: String,
    val nim: String
)