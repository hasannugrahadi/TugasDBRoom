package com.app.tugaskelompok

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.tugaskelompok.database.User
import com.app.tugaskelompok.database.UserRoomDatabase
import com.app.tugaskelompok.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var usergithubEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var nimEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var backButton: ImageButton
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val database = UserRoomDatabase.getDatabase(applicationContext)
        val userDao = database.UserDao()
        auth = Firebase.auth
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        usernameEditText = findViewById(R.id.register_username)
        passwordEditText = findViewById(R.id.register_password)
        usergithubEditText = findViewById(R.id.register_usergithub)
        emailEditText = findViewById(R.id.register_email)
        nimEditText = findViewById(R.id.register_NIM)
        registerButton = findViewById(R.id.register_button)

        binding.registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val usergithub = usergithubEditText.text.toString()
            val email = emailEditText.text.toString()
            val nim = nimEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && usergithub.isNotEmpty() && email.isNotEmpty() && nim.isNotEmpty()) {
                // Check if the username is already in use
                CoroutineScope(Dispatchers.IO).launch {
                    val existingUser = userDao.getUserByUsername(username)
                    if (existingUser == null) {
                        // User doesn't exist, proceed with registration
                        val newUser = User(userApp = username, password = password, userGithub = usergithub, email = email, nim = nim)
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
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                auth.signOut()
                Toast.makeText(this, "Akun Berhasil Dibuat!!", Toast.LENGTH_SHORT).show()
            }else{
                Log.e("error: ", it.exception.toString())
                }
            }
        }

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
//            reload()
        }
    }

}