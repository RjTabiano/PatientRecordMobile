package com.example.patientrecord.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patientrecord.R
import com.example.patientrecord.controller.BookingController
import com.example.patientrecord.model.Booking
import com.example.patientrecord.network.ApiCallback
import com.example.patientrecord.network.ApiResponse
import com.example.patientrecord.network.TokenManager
import com.example.patientrecord.ui.AppointmentAdapter

class AppointmentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewAppointments)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchAndPopulateAppointments()

        return view
    }

    private fun fetchAndPopulateAppointments() {
        val token = TokenManager.getToken(requireContext())
        if (token != null) {
            val bookingController = BookingController(requireContext())
            bookingController.getBooking(token, object : ApiCallback<List<Booking>> {
                override fun onSuccess(response: List<Booking>) {
                    val adapter = AppointmentAdapter(response)
                    recyclerView.adapter = adapter
                }

                override fun onFailure(error: ApiResponse) {
                    Log.e("AppointmentFragment", "Failed to fetch appointments: ${error.message}")
                }
            })
        } else {
            Log.e("AppointmentFragment", "Token not available")
        }
    }
}