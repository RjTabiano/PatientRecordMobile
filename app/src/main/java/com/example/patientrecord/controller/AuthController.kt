package com.example.patientrecord.controller

import android.content.Context
import com.example.patientrecord.model.User
import com.example.patientrecord.network.ApiCallback
import com.example.patientrecord.network.ApiResponse
import com.example.patientrecord.network.ApiService
import com.example.patientrecord.network.TokenManager
import com.example.patientrecord.ui.UserView.UserHomeActivity

class AuthController(private val context: Context)  {
    fun loginUser(email: String, password: String, callback: ApiCallback<User>) {
        ApiService.loginUser(context, email, password, callback)
    }

    fun registerUser(name: String, email: String, password: String, callback: ApiCallback<User>) {
        ApiService.registerUser(name, email, password, callback)
    }

    fun getLoggedInUser(activity: UserHomeActivity, token: String) {
        ApiService.getLoggedInUser(token, object : ApiCallback<User> {
            override fun onSuccess(response: User) {
                activity.updateUserInfo(response.name, response.email)
            }

            override fun onFailure(error: ApiResponse) {
                println("Failed to get logged-in user info: ${error.message}")
            }
        })
    }

    fun logout() {

        TokenManager.clearToken(context)
    }

}