package com.example.mypokedexapp.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokedexViewModel : ViewModel() {
    private val pokemonRepository = PokemonRepository()

    private val _pokemon = MutableLiveData<ArrayList<Pokemon>>().apply{
        pokemonRepository.getPokemonList { pokemon ->
            value = pokemon
        }
    }

    val pokemon: LiveData<ArrayList<Pokemon>>
        get() = _pokemon

}