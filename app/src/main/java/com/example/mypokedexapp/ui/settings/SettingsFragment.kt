package com.example.mypokedexapp.ui.settings

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.mypokedexapp.R
import com.example.mypokedexapp.ui.colorpicker.ColorPicker
import com.example.mypokedexapp.ui.colorpicker.ColorPickerCallback
import com.example.mypokedexapp.utils.ColorHelper
import com.example.mypokedexapp.values.PreferenceKeys


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val currentColor = Color.parseColor(prefs.getString(PreferenceKeys.COLOR, PreferenceKeys.DEFAULT_COLOR))
        if (preference?.key == PreferenceKeys.COLOR) {
            val colorPicker = ColorPicker(requireActivity(), currentColor)
            colorPicker.setCallback(object : ColorPickerCallback {
                override fun onColorChosen(color: Int) {
                    val edit: SharedPreferences.Editor = prefs.edit()
                    edit.putString(PreferenceKeys.COLOR, "#${ColorHelper.colorToHex(color)}")
                    edit.apply()
                }
            })
            colorPicker.show()
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }
}