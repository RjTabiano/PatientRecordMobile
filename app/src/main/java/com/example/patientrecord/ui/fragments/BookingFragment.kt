package com.example.patientrecord.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.patientrecord.R
import com.example.patientrecord.controller.BookingController
import com.example.patientrecord.model.Booking
import com.example.patientrecord.network.ApiCallback
import com.example.patientrecord.network.ApiResponse
import com.example.patientrecord.network.TokenManager
import java.util.Calendar

class BookingFragment : Fragment() {
    private lateinit var spinner: Spinner
    private lateinit var buttonBookNow: Button
    private lateinit var textViewDate: TextView
    private lateinit var textViewTime: TextView
    private lateinit var textViewNumber: EditText
    private lateinit var bookingController: BookingController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        spinner = view.findViewById(R.id.spinId)
        buttonBookNow = view.findViewById(R.id.buttonBookNow)
        textViewDate = view.findViewById(R.id.textViewDate)
        textViewTime = view.findViewById(R.id.textViewTime)
        textViewNumber = view.findViewById(R.id.textViewNumber)

        bookingController = BookingController(requireContext())
        val token = TokenManager.getToken(requireContext())

        val services = arrayOf("Pediatrics", "Obgyne", "Consultation")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, services)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Set OnClickListener for date TextView
        textViewDate.setOnClickListener {
            showDatePicker()
        }

        // Set OnClickListener for time TextView
        textViewTime.setOnClickListener {
            showTimePicker()
        }

        // Set OnClickListener for book button
        buttonBookNow.setOnClickListener {
            val selectedService = spinner.selectedItem.toString()
            val selectedDate = textViewDate.text.toString()
            val selectedTime = textViewTime.text.toString()
            val selectedNumber = textViewNumber.text.toString()

            // Call bookUser function from BookingController to make the booking
            if (token != null) {
                bookingController.bookUser(selectedService, selectedDate, selectedTime, selectedNumber, token, object : ApiCallback<Booking> {
                    override fun onSuccess(response: Booking) {
                        Toast.makeText(requireContext(), "Booking successful!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(error: ApiResponse) {
                        Toast.makeText(requireContext(), "Booking failed. Please try again later.", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(requireContext(), "Session expired. Please log in again.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Handle date selection here
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
                // Set selected date to textViewDate
                textViewDate.text = selectedDate
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                // Handle time selection here
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                // Set selected time to textViewTime
                textViewTime.text = formattedTime
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }
}