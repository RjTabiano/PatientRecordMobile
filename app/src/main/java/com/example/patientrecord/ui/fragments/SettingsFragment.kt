package com.example.patientrecord.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.patientrecord.R
import com.example.patientrecord.model.UpdateUser
import com.example.patientrecord.network.ApiInterface
import com.example.patientrecord.network.RetrofitClient
import com.example.patientrecord.network.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsFragment : Fragment() {
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonUpdate: Button
    private lateinit var apiInterface: ApiInterface
    private var userInfoUpdateListener: UserInfoUpdateListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize views
        editTextName = view.findViewById(R.id.edit_text_name)
        editTextEmail = view.findViewById(R.id.edit_text_email)
        editTextPassword = view.findViewById(R.id.edit_text_password)
        editTextConfirmPassword = view.findViewById(R.id.edit_text_confirm_password)
        buttonUpdate = view.findViewById(R.id.button_update)

        // Initialize Retrofit interface
        apiInterface = RetrofitClient.create(ApiInterface::class.java)

        buttonUpdate.setOnClickListener {
            updateUser()
        }

        return view
    }

    private fun updateUser() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()

        val token = TokenManager.getToken(requireContext())

        // Call the API to update user information
        val call = apiInterface.updateUser("Bearer $token", name, email, password, confirmPassword)
        call.enqueue(object : Callback<UpdateUser> {
            override fun onResponse(call: Call<UpdateUser>, response: Response<UpdateUser>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show()
                    userInfoUpdateListener?.onUserInfoUpdated(name, email)
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(context, "Failed to update user: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateUser>, t: Throwable) {
                // Handle failure
                Toast.makeText(context, "Failed to update user: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserInfoUpdateListener) {
            userInfoUpdateListener = context
        } else {
            throw RuntimeException("$context must implement UserInfoUpdateListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        userInfoUpdateListener = null
    }

    interface UserInfoUpdateListener {
        fun onUserInfoUpdated(name: String, email: String)
    }

}

