package com.example.mypokedexapp.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.mypokedexapp.R
import com.example.mypokedexapp.ui.profile.ProfileViewModel
import com.example.mypokedexapp.ui.profile.ProfileViewModelFactory

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.layout.fragment_settings, rootKey)
    }
}