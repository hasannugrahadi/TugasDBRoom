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
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.tugaskelompok.database.UserDao
import com.app.tugaskelompok.database.UserRoomDatabase
import com.app.tugaskelompok.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView
//    private lateinit var GoogleSignInClient: GoogleSignInClient;
    private lateinit var database: UserRoomDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = UserRoomDatabase.getDatabase(applicationContext)
        userDao = database.UserDao()

        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        registerText = findViewById(R.id.openRegister)

        passwordEditText.transformationMethod = AsteriskPasswordTransformationMethod()

        binding.loginButton.setOnClickListener {
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
            val  email = binding.loginUsername.text.toString()
            val  pw = binding.loginPassword.text.toString()
            auth.signInWithEmailAndPassword(email,pw).addOnCompleteListener {
                if(it.isSuccessful){
Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                }
            }
            BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.clientid))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build()

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
class AsteriskPasswordTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(source)
    }

    inner class PasswordCharSequence (private val source: CharSequence) : CharSequence {

        override val length: Int
            get() = source.length

        override fun get(index: Int): Char = '*'

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return source.subSequence(startIndex, endIndex)
        }

    }

}
