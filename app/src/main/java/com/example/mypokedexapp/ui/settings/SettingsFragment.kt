package com.example.mypokedexapp.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R
import com.example.mypokedexapp.ui.profile.ProfileViewModel
import com.example.mypokedexapp.ui.profile.ProfileViewModelFactory

class SettingsFragment : Fragment() {
    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory((activity?.application as PokemonApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_settings)
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}