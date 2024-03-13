package com.example.patientrecord.network

import android.content.Context
import java.io.File

object TokenManager {
    private const val FILE_NAME = "token.txt"

    fun saveToken(context: Context, token: String) {
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(token)
    }

    fun getToken(context: Context): String? {
        val file = File(context.filesDir, FILE_NAME)
        return if (file.exists()) {
            file.readText()
        } else {
            null
        }
    }

    fun clearToken(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) {
            file.delete()
        }
    }
}