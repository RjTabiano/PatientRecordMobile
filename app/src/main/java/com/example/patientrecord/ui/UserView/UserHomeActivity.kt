package com.example.patientrecord.ui.UserView

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.patientrecord.R
import com.example.patientrecord.controller.AuthController
import com.example.patientrecord.network.TokenManager
import com.example.patientrecord.network.TokenManager.clearToken
import com.example.patientrecord.ui.auth.LoginActivity
import com.example.patientrecord.ui.fragments.AppointmentFragment
import com.example.patientrecord.ui.fragments.BookingFragment
import com.example.patientrecord.ui.fragments.ConsultationFragment
import com.example.patientrecord.ui.fragments.HomeFragment
import com.example.patientrecord.ui.fragments.RecordFragment
import com.example.patientrecord.ui.fragments.SettingsFragment
import com.google.android.material.navigation.NavigationView

class UserHomeActivity : AppCompatActivity(), SettingsFragment.UserInfoUpdateListener {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout


    private lateinit var headerUserName: TextView
    private lateinit var headerUserEmail: TextView

    private val authController by lazy { AuthController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_home)


        drawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_View)

        toggle = ActionBarDrawerToggle(this ,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val headerView = navView.getHeaderView(0)
        headerUserName = headerView.findViewById(R.id.user_name)
        headerUserEmail = headerView.findViewById(R.id.user_email)






        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_records -> replaceFragment(RecordFragment(), it.title.toString())
                R.id.nav_appointment -> replaceFragment(AppointmentFragment(), it.title.toString())
                R.id.nav_consultation -> replaceFragment(ConsultationFragment(), it.title.toString())
                R.id.nav_booking -> replaceFragment(BookingFragment(), it.title.toString())
                R.id.nav_settings -> replaceFragment(SettingsFragment(), it.title.toString())
                R.id.nav_logout -> logout()

            }

            true
        }

        val token = TokenManager.getToken(this)
        token?.let {
            authController.getLoggedInUser(this, token)
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        val token = TokenManager.getToken(this)
        if (token != null) {
            clearToken(this)
            Toast.makeText(applicationContext, "Logged out successfully", Toast.LENGTH_SHORT).show()
            authController.logout()

            startActivity(Intent(this, LoginActivity::class.java))

            finish()
        } else {
            // Token not found, prompt the user to log in manually
            // Optionally, you can show a login screen or handle the absence of a token in another way
            Toast.makeText(applicationContext, "Please log in to continue.", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateUserInfo(name: String, email: String) {
        headerUserName.text = name
        headerUserEmail.text = email
    }


    override fun onUserInfoUpdated(name: String, email: String) {
        headerUserName.text = name
        headerUserEmail.text = email
    }

}