package com.example.mypokedexapp.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokemonDetailViewModel(id: Number) : ViewModel() {
    private val pokemonRepository = PokemonRepository()

    private val _pokemon = MutableLiveData<Pokemon>().apply{
        pokemonRepository.getPokemonList { pokemon ->
            value = pokemon
        }
    }

    val pokemon: LiveData<ArrayList<Pokemon>>
        get() = _pokemon
}