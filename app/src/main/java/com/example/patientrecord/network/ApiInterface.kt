package com.example.patientrecord.network

import com.example.patientrecord.model.Booking
import com.example.patientrecord.model.RecordImage
import com.example.patientrecord.model.UpdateUser
import com.example.patientrecord.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>

    @GET("user_account")
    fun getLoggedInUser(@Header("Authorization") token: String): Call<User>

    @FormUrlEncoded
    @POST("book")
    fun booking(
        @Header("Authorization") token: String,
        @Field("service") service: String,
        @Field("date") date: String,
        @Field("time") time: String
    ): Call<Booking>


    @GET("booked")
    fun getBooking(@Header("Authorization") token: String): Call<List<Booking>>


    @GET("getImage")
    fun getPediatrics(@Header("Authorization") token: String): Call<RecordImage>


    @FormUrlEncoded
    @POST("updateUser")
    fun updateUser(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm password") confirmPassword: String
    ): Call<UpdateUser>
}