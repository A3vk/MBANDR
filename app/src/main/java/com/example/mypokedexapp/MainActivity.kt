package com.example.mypokedexapp

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener{
    private var currentLanguage = "en"
    private var currentLang: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentLanguage = intent.getStringExtra(currentLang).toString()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPref.registerOnSharedPreferenceChangeListener(this)
        setTopBarColor(sharedPref)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor(
            sharedPref.getString(
                "color_preference",
                "#FF6200EE"
            )
        )

        setApplicationLocale("nl")

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        setBottomMenuColor(sharedPref)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navView.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
   }

    private fun setTopBarColor(sharedPref: SharedPreferences){
        val color = sharedPref.getString("color_preference", "FF6200EE")
        val colorDrawable = ColorDrawable(Color.parseColor(color))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
    }

    private fun setBottomMenuColor(sharedPref: SharedPreferences){
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val color = sharedPref.getString("color_preference", "FF6200EE")

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
            setBottomMenuColor(sharedPreferences)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(sharedPreferences.getString(key, "#FF6200EE"))
        }
        if (key == "language_preference"){
            setApplicationLocale(sharedPreferences?.getString(key, "nl").toString())
        }
    }

    private fun setApplicationLocale(locale: String) {
        println (currentLanguage)
        println(locale)
        if(locale != currentLanguage) {
            val resources: Resources = resources
            val config: Configuration = resources.configuration
            config.setLocale(Locale(locale?.toLowerCase()))
            resources.configuration.updateFrom(config)
            val refresh = Intent(this, MainActivity::class.java)
            startActivity(refresh)
        }
    }
}