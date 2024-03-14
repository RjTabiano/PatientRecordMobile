package com.example.patientrecord.network
import android.content.Context
import android.util.Log
import com.example.patientrecord.model.Booking
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

    fun bookUser(service: String, date: String, time: String, token: String, callback: ApiCallback<Booking>) {
        val call = apiService.booking("Bearer $token", service, date, time)
        call.enqueue(object : Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                if (response.isSuccessful) {
                    val booking = response.body()
                    booking?.let {
                        callback.onSuccess(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("API_RESPONSE", "Booking failed: $errorMessage")
                    callback.onFailure(ApiResponse(errorMessage ?: "Booking failed"))
                }
            }

            override fun onFailure(call: Call<Booking>, t: Throwable) {
                Log.e("API_RESPONSE", "Booking failed: ${t.message}", t)
                callback.onFailure(ApiResponse(t.message ?: "Booking failed"))
            }
        })
    }

    fun getBooking(token: String, callback: ApiCallback<List<Booking>>) {
        val call = apiService.getBooking("Bearer $token")
        call.enqueue(object : Callback<List<Booking>> {
            override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
                if (response.isSuccessful) {
                    val bookings = response.body()
                    Log.d("API_RESPONSE", "Bookings received: $bookings")

                    bookings?.let {
                        callback.onSuccess(it)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("API_RESPONSE", "Get bookings failed: $errorMessage")
                    callback.onFailure(ApiResponse(errorMessage ?: "Get bookings failed"))
                }
            }

            override fun onFailure(call: Call<List<Booking>>, t: Throwable) {
                Log.e("API_RESPONSE", "Get bookings failed: ${t.message}", t)
                callback.onFailure(ApiResponse(t.message ?: "Get bookings failed"))
            }
        })
    }
}