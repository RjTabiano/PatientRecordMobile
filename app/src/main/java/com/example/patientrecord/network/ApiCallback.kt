package com.example.patientrecord.network


interface ApiCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(error: ApiResponse)
}
