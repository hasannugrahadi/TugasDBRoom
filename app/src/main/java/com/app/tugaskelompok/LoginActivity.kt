package com.app.tugaskelompok

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "hasan" && password == "1234") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Lu bau", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeTextLink(textView: TextView, str: String, underlined: Boolean, color: Int?, action: (() -> Unit)? = null) {
        val spannableString = SpannableString(textView.text)
        val textColor = color ?: textView.currentTextColor
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                action?.invoke()
            }
            override fun updateDrawState(drawState: TextPaint) {
                super.updateDrawState(drawState)
                drawState.isUnderlineText = underlined
                drawState.color = textColor
            }
        }
        val index = spannableString.indexOf(str)
        spannableString.setSpan(clickableSpan, index, index + str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
    }
}
