package com.example.mypokedexapp.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokedexViewModel : ViewModel() {
    private val pokemonRepository = PokemonRepository()
    private val _pokemon = MutableLiveData<List<Pokemon>>().apply{
        pokemonRepository.getPokemon { onComplete ->
            value = onComplete
        }
    }
    private val _text = MutableLiveData<String>().apply {
        val pokemon = pokemonRepository.getPokemon { onComplete ->
            value = onComplete[0].name
        }
        value = "This is pokedex Fragment"
    }

    val text: LiveData<String> = _text
    val pokemon: LiveData<List<Pokemon>>
        get() = _pokemon

}