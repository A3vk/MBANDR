package com.example.mypokedexapp

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.mypokedexapp.utils.ColorHelper
import com.example.mypokedexapp.values.PreferenceKeys
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPref.registerOnSharedPreferenceChangeListener(this)
        updateColors(sharedPref)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navView.menu)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
   }

    private fun updateColors(sharedPref: SharedPreferences) {
        setTopBarColor(sharedPref)
        setBottomMenuColor(sharedPref)
        setStatusBarColor(sharedPref)
    }

    private fun setTopBarColor(sharedPref: SharedPreferences){
        val color = sharedPref.getString(PreferenceKeys.COLOR, PreferenceKeys.DEFAULT_COLOR)
        val colorDrawable = ColorDrawable(Color.parseColor(color))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
    }

    private fun setBottomMenuColor(sharedPref: SharedPreferences){
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val color = sharedPref.getString(PreferenceKeys.COLOR, PreferenceKeys.DEFAULT_COLOR)

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

    private fun setStatusBarColor(sharedPref: SharedPreferences) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ColorHelper.darken(Color.parseColor(sharedPref.getString(PreferenceKeys.COLOR, PreferenceKeys.DEFAULT_COLOR)), 0.2f)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key) {
            PreferenceKeys.COLOR -> {
                updateColors(sharedPreferences!!)
            }
            PreferenceKeys.LANGUAGE -> {
                recreate()
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(updateBaseContextLocale(base!!))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val locale = Locale(sharedPref.getString(PreferenceKeys.LANGUAGE, PreferenceKeys.DEFAULT_LANGUAGE)!!)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    // Handle back button press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}