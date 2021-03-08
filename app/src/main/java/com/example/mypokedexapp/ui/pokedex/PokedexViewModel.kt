package com.example.mypokedexapp.ui.pokedex

import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository

class PokedexViewModel(private val pokemonRepository: PokemonRepository): ViewModel() {
    private val offset = 20
    private var totalPokemon = 0

    val pokemon: LiveData<ArrayList<Pokemon>>
        get() = _pokemon

    private val _pokemon = MutableLiveData<ArrayList<Pokemon>>().apply{
        value = ArrayList()
        pokemonRepository.getPokemonList(totalPokemon) { pokemon ->
            value?.add(pokemon)
            value?.sort()
            value = value
        }
        totalPokemon += offset
    }

    fun getNextPokemon() {
        if (totalPokemon == _pokemon.value?.count()) {
            _pokemon.apply {
                pokemonRepository.getPokemonList(totalPokemon) { pokemon ->
                    value?.add(pokemon)
                    value?.sort()
                    value = value
                }
            }
            totalPokemon += offset
        }
    }
}

class PokedexViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}