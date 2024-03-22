package com.example.patientrecord.controller

import android.content.Context
import com.example.patientrecord.model.Booking
import com.example.patientrecord.network.ApiCallback
import com.example.patientrecord.network.ApiService

class BookingController(private val context: Context) {

    fun bookUser(service: String, date: String, time: String, phoneNumber: Number, token: String, callback: ApiCallback<Booking>) {
        ApiService.bookUser(service, date, time, phoneNumber, token, callback)
    }

    fun getBooking(token: String, callback: ApiCallback<List<Booking>>) {
        ApiService.getBooking(token, callback)
    }
 }