package com.example.mypokedexapp

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        setTopBarColor(sharedPref)
        sharedPref.registerOnSharedPreferenceChangeListener(object :SharedPreferences.OnSharedPreferenceChangeListener{
            override fun onSharedPreferenceChanged(sharedPref: SharedPreferences, key: String) {
                if(key == "color_preference"){
                    setTopBarColor(sharedPref)
                }
            }
        })

       val navView: BottomNavigationView = findViewById(R.id.nav_view)
       val navController = findNavController(R.id.nav_host_fragment)
       val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_pokedex, R.id.navigation_team, R.id.navigation_profile, R.id.navigation_settings))
       setupActionBarWithNavController(navController, appBarConfiguration)
       navView.setupWithNavController(navController)
   }

    private fun setTopBarColor(sharedPref: SharedPreferences){
        val color = sharedPref.getString("color_preference", "#000000")
        val colorDrawable = ColorDrawable(Color.parseColor(color))
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(colorDrawable)
    }
}