package com.example.patientrecord.ui.UserView

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.patientrecord.R
import com.google.android.material.navigation.NavigationView

class UserHomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_home)


        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_View)

        toggle = ActionBarDrawerToggle(this ,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_records -> Toast.makeText(applicationContext, "Clicked My Patient Records", Toast.LENGTH_SHORT).show()
                R.id.nav_appointment -> Toast.makeText(applicationContext, "Clicked Appointments", Toast.LENGTH_SHORT).show()
                R.id.nav_booking -> Toast.makeText(applicationContext, "Clicked Book now", Toast.LENGTH_SHORT).show()
                R.id.nav_settings -> Toast.makeText(applicationContext, "Clicked Settings", Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> Toast.makeText(applicationContext, "Clicked Log out", Toast.LENGTH_SHORT).show()

            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}