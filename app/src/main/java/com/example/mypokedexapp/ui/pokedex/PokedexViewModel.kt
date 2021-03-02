package com.example.mypokedexapp.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.repositories.PokemonRepository

class PokedexViewModel : ViewModel() {
    private val pokemonRepository = PokemonRepository()

    private val _text = MutableLiveData<String>().apply {
        pokemonRepository.getPokemon()
        value = "This is pokedex Fragment"
    }
    val text: LiveData<String> = _text
}