package com.example.mypokedexapp

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPref.registerOnSharedPreferenceChangeListener(this)
        setTopBarColor(sharedPref)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        setBottomMenuColor(sharedPref)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_pokedex,
                R.id.navigation_team,
                R.id.navigation_profile,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
   }

    private fun setTopBarColor(sharedPref: SharedPreferences){
        val color = sharedPref.getString("color_preference", "#000000")
        val colorDrawable = ColorDrawable(Color.parseColor(color))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
    }


    private fun setBottomMenuColor(sharedPref: SharedPreferences){
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val color = sharedPref.getString("color_preference", "#000000")

        val states = arrayOf(
            intArrayOf(android.R.attr.state_selected),
            intArrayOf(-android.R.attr.state_selected),
        )

        val colors = intArrayOf(
            Color.parseColor(color),
            R.color.design_default_color_primary
        )

        val colorStateList = ColorStateList(states, colors)
        navView.itemTextColor = colorStateList
        navView.itemIconTintList = colorStateList
        println(navView.itemTextColor)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "color_preference") {
            setTopBarColor(sharedPreferences!!)
            setBottomMenuColor(sharedPreferences!!)
        }
    }
}