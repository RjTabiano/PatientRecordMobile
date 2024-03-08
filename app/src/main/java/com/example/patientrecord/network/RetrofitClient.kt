package com.example.patientrecord.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.mockfly.dev/mocks/9dc8d068-64e0-4a0a-9e6a-903130633e28/"

    fun create(apiInterface: Class<ApiInterface>): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(apiInterface)
    }
}