package com.example.patientrecord.ui.UserView

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.patientrecord.R
import com.example.patientrecord.ui.fragments.AppointmentFragment
import com.example.patientrecord.ui.fragments.BookingFragment
import com.example.patientrecord.ui.fragments.ConsultationFragment
import com.example.patientrecord.ui.fragments.HomeFragment
import com.example.patientrecord.ui.fragments.ProfileFragment
import com.example.patientrecord.ui.fragments.RecordFragment
import com.google.android.material.navigation.NavigationView

class UserHomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
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

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_records -> replaceFragment(RecordFragment(), it.title.toString())
                R.id.nav_appointment -> replaceFragment(AppointmentFragment(), it.title.toString())
                R.id.nav_consultation -> replaceFragment(ConsultationFragment(), it.title.toString())
                R.id.nav_booking -> replaceFragment(BookingFragment(), it.title.toString())
                R.id.nav_settings -> replaceFragment(ProfileFragment(), it.title.toString())
                R.id.nav_logout -> Toast.makeText(applicationContext, "LOG OUT", Toast.LENGTH_SHORT).show()

            }

            true
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
}