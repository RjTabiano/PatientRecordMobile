package com.example.patientrecord.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.patientrecord.R
import com.example.patientrecord.model.Booking

class AppointmentAdapter(private val bookings: List<Booking>) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookings[position]
        // Bind data to views in the ViewHolder
        holder.bind(booking)
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviceTextView: TextView = itemView.findViewById(R.id.serviceTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)

        fun bind(booking: Booking) {
            // Bind booking data to TextViews
            serviceTextView.text = booking.service
            dateTextView.text = booking.date
            timeTextView.text = booking.time
        }
    }
}