package com.example.patientrecord.model

data class UpdateUser(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
