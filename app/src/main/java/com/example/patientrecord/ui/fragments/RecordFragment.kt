package com.example.patientrecord.ui.fragments

import android.os.Bundle
import android.util.Log
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
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        imageAdapter = ImageAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = imageAdapter
        }

        fetchUserImages()
    }

    private fun fetchUserImages() {
        val apiService = RetrofitClient.create(ApiInterface::class.java)
        val token = TokenManager.getToken(requireContext())
        val call = apiService.getUserImages("Bearer $token")

        call.enqueue(object : Callback<List<RecordImage>> {
            override fun onResponse(
                call: Call<List<RecordImage>>,
                response: Response<List<RecordImage>>
            ) {
                Log.e("response", "response $response")
                if (response.isSuccessful) {
                    val images = response.body()
                    images?.let {
                        imageAdapter.submitList(it)
                    }
                    Log.e("response", "response $images")
                } else {
                    Log.e("101", "ERROR")
                }
            }

            override fun onFailure(call: Call<List<RecordImage>>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch user images", t)

            }
        })
    }
}