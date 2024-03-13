package com.example.patientrecord.model

data class User(
    val name: String,
    val email: String,
    val password: String,
    val usertype: String = "user",
    val token: String?
) {

}