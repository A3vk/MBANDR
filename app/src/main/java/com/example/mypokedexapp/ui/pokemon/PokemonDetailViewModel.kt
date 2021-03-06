package com.example.mypokedexapp.ui.pokemon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokemonDetailViewModel(app: Application) : AndroidViewModel(app) {
    private val pokemonRepository = PokemonRepository(app.applicationContext)

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    fun setPokemon(id: Int) {
        println(id)
        _pokemon.apply {
            pokemonRepository.getPokemon(id) { pokemon ->
                value = pokemon
            }
        }
    }
}