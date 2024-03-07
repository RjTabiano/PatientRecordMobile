package com.example.patientrecord.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.mockfly.dev/mocks/282f8108-862a-4ca1-88b4-f4aae40d5dbc/"

    fun create(apiInterface: Class<ApiInterface>): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(apiInterface)
    }
}