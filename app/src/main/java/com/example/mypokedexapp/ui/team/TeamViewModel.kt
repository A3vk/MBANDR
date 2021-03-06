package com.example.mypokedexapp.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypokedexapp.repositories.PokemonRepository
import com.example.mypokedexapp.ui.profile.ProfileViewModel

class TeamViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is team Fragment"
    }
    val text: LiveData<String> = _text
}

class TeamViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}