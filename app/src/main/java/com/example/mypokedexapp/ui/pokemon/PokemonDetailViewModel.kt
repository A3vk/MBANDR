package com.example.mypokedexapp.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokemonDetailViewModel : ViewModel() {
    private val pokemonRepository = PokemonRepository()

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    fun setPokemon(id: Int) {
        _pokemon.apply {
            pokemonRepository.getPokemon(id) { pokemon ->
                value = pokemon
            }
        }
    }
}