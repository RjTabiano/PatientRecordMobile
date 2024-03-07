package com.example.patientrecord.controller

import com.example.patientrecord.model.User
import com.example.patientrecord.network.ApiCallback
import com.example.patientrecord.network.ApiService

class AuthController {
    fun loginUser(email: String, password: String, callback: ApiCallback<User>) {
        ApiService.loginUser(email, password, callback)
    }

    fun registerUser(name: String, email: String, password: String, callback: ApiCallback<User>) {
        ApiService.registerUser(name, email, password, callback)
    }
}