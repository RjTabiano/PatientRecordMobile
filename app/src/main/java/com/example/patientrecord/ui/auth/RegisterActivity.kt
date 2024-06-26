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

class RegisterActivity : AppCompatActivity() {

    private val authController = AuthController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val userNameField = findViewById<EditText>(R.id.registerNameField)
        val userEmailField = findViewById<EditText>(R.id.registerEmailField)
        val passwordField = findViewById<EditText>(R.id.registerPasswordField)
        val confirmPasswordField = findViewById<EditText>(R.id.registerConfirmPasswordField)

        buttonRegister.setOnClickListener {
            val name = userNameField.text.toString()
            val email = userEmailField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            // Check if password and confirm password match
            if (password != confirmPassword) {
                Toast.makeText(this@RegisterActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the click listener
            }

            authController.registerUser(name, email, password, object : ApiCallback<User> {
                override fun onSuccess(response: User) {
                    Toast.makeText(this@RegisterActivity, "Register successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: Close the current activity if necessary
                }

                override fun onFailure(error: ApiResponse) {
                    Toast.makeText(this@RegisterActivity, "Register failed: $error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
