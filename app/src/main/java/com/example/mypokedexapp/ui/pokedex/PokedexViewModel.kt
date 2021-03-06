package com.example.mypokedexapp.ui.pokedex

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokedexViewModel(app: Application): AndroidViewModel(app) {
    private val pokemonRepository = PokemonRepository(app.applicationContext)
    private val offset = 20
    private var totalPokemon = 0
    private var test = 0
    private val _pokemon = MutableLiveData<ArrayList<Pokemon>>().apply{
        value = ArrayList()
        pokemonRepository.getPokemonList(totalPokemon) { pokemon ->
            value?.add(pokemon)
            value?.sort()
            value = value
        }
        totalPokemon += offset

        pokemonRepository.savePokemon(value?.first() as Pokemon)
        println(pokemonRepository.getSavedPokemon(1)?.name)
    }

    val pokemon: LiveData<ArrayList<Pokemon>>
        get() = _pokemon

    fun getNextPokemon() {
        if (totalPokemon == _pokemon.value?.count()) {
            test += 1
            _pokemon.apply {
                pokemonRepository.getPokemonList(totalPokemon) { pokemon ->
                    value?.add(pokemon)
                    value?.sort()
                }
            }
            totalPokemon += offset
        }
    }
}