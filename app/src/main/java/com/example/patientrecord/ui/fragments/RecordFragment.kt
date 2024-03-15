package com.example.patientrecord.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patientrecord.ImageAdapter
import com.example.patientrecord.R
import com.example.patientrecord.model.RecordImage
import com.example.patientrecord.network.ApiInterface
import com.example.patientrecord.network.RetrofitClient
import com.example.patientrecord.network.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecordFragment : Fragment() {

    private lateinit var apiInterface: ApiInterface
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_record, container, false)

        recyclerView = view.findViewById(R.id.recordRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        imageAdapter = ImageAdapter(requireContext())
        recyclerView.adapter = imageAdapter

        apiInterface = RetrofitClient.create(ApiInterface::class.java)
        fetchImageData()

        return view
    }

    private fun fetchImageData() {
        val token = TokenManager.getToken(requireContext())
        val call = apiInterface.getPediatrics("Bearer $token")

        call.enqueue(object : Callback<RecordImage> {
            override fun onResponse(call: Call<RecordImage>, response: Response<RecordImage>) {
                if (response.isSuccessful) {
                    val imageData = response.body()?.imageData
                    if (!imageData.isNullOrEmpty()) {
                        val decodedBytes = Base64.decode(imageData, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        // Update adapter with decoded bitmap
                        imageAdapter.updateImageData(bitmap)
                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<RecordImage>, t: Throwable) {
                // Handle failure
            }
        })
    }
}