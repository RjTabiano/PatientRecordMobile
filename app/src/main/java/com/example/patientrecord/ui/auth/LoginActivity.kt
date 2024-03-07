package com.example.patientrecord.ui.auth

import android.content.Intent
import android.os.Bundle
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
import com.example.patientrecord.ui.UserView.UserHomeActivity


class LoginActivity : AppCompatActivity() {

    private val authController = AuthController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val userEmailField = findViewById<EditText>(R.id.userEmailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        buttonLogin.setOnClickListener {
            val email = userEmailField.text.toString()
            val password = passwordField.text.toString()

            authController.loginUser(email, password, object : ApiCallback<User> {
                override fun onSuccess(response: User) {
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, UserHomeActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: Close the current activity if necessary
                }

                override fun onFailure(error: ApiResponse) {
                    Toast.makeText(this@LoginActivity, "Login failed: $error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}