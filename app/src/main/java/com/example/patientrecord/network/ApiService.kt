package com.example.patientrecord.network
import android.content.Context
import android.util.Log
import com.example.patientrecord.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiService {

    private val apiService = RetrofitClient.create(ApiInterface::class.java)



    fun loginUser(context: Context, email: String, password: String, callback: ApiCallback<User>) {
        val call = apiService.loginUser(email, password)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        val token = it.token
                        token?.let {
                            TokenManager.saveToken(context, token)
                        }
                        callback.onSuccess(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("API_RESPONSE", "Login failed: $errorMessage")
                    callback.onFailure(ApiResponse(errorMessage))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("API_RESPONSE", "Login failed: ${t.message}", t)
                callback.onFailure(ApiResponse(t.message))
            }
        })
    }


    fun registerUser(name: String, email: String, password: String, callback: ApiCallback<User>) {

        val call = apiService.registerUser(name, email, password)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val registeredUser = response.body()
                    registeredUser?.let {
                        callback.onSuccess(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("API_RESPONSE", "Registration failed: $errorMessage")
                    callback.onFailure(ApiResponse(errorMessage))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("API_RESPONSE", "Registration failed: ${t.message}", t)
                callback.onFailure(ApiResponse(t.message))
            }
        })
    }

    fun getLoggedInUser(token: String, callback: ApiCallback<User>) {
        val call = apiService.getLoggedInUser("Bearer $token")
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        callback.onSuccess(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("API_RESPONSE", "Get logged-in user failed: $errorMessage")
                    callback.onFailure(ApiResponse(errorMessage))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("API_RESPONSE", "Get logged-in user failed: ${t.message}", t)
                callback.onFailure(ApiResponse(t.message))
            }
        })
    }
}