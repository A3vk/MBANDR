package com.example.mypokedexapp.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokedexViewModel : ViewModel() {
    private val pokemonRepository = PokemonRepository()
    private val offset = 20
    private var totalPokemon = 0
    private var test = 0
    private val _pokemon = MutableLiveData<ArrayList<Pokemon>>().apply{
        value = ArrayList()
        pokemonRepository.getPokemon(totalPokemon) { pokemon ->
            value?.add(pokemon)
            value?.sort()
            value = value
        }
        totalPokemon += offset
    }

    val pokemon: LiveData<ArrayList<Pokemon>>
        get() = _pokemon

    fun getNextPokemon() {
        if (totalPokemon == _pokemon.value?.count()) {
            test += 1
            _pokemon.apply {
                pokemonRepository.getPokemon(totalPokemon) { pokemon ->
                    value?.add(pokemon)
                    value?.sort()
                }
            }
            totalPokemon += offset
        }
    }
}