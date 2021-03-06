package com.example.mypokedexapp.ui.pokemon

import android.app.Application
import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository
import com.example.mypokedexapp.ui.pokedex.PokedexViewModel

class PokemonDetailViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _pokemon = MutableLiveData<Pokemon>()

    fun setPokemon(id: Int) {
        println(id)
        _pokemon.apply {
            pokemonRepository.getPokemon(id) { pokemon ->
                value = pokemon
            }
        }
    }
}

class PokemonDetailViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}