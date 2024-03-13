package com.example.patientrecord.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.patientrecord.R
import com.example.patientrecord.controller.AuthController
import com.example.patientrecord.model.User
import com.example.patientrecord.network.ApiCallback
import com.example.patientrecord.network.ApiResponse
import com.example.patientrecord.network.TokenManager.getToken
import com.example.patientrecord.ui.UserView.UserHomeActivity


class LoginActivity : AppCompatActivity() {

    private val authController = AuthController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val token = getToken(this)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val userEmailField = findViewById<EditText>(R.id.userEmailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        buttonLogin.setOnClickListener {
            val email = userEmailField.text.toString()
            val password = passwordField.text.toString()

            authController.loginUser(email, password, object : ApiCallback<User> {
                override fun onSuccess(response: User) {
                        val intent = Intent(this@LoginActivity, UserHomeActivity::class.java)
                        Toast.makeText(this@LoginActivity, "Log in successful!", Toast.LENGTH_SHORT).show()

                        startActivity(intent)
                        finish()
                }

                override fun onFailure(error: ApiResponse) {
                    Toast.makeText(this@LoginActivity, "Login failed: $error", Toast.LENGTH_SHORT).show()
                    Log.e("LoginActivity", "Login failed: $error")

                }
            })
        }
    }

    fun onSignUpClicked(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}