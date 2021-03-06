package com.example.mypokedexapp.ui.pokedex

import android.app.Application
import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.models.Type
import com.example.mypokedexapp.repositories.PokemonRepository
import kotlinx.coroutines.launch

class PokedexViewModel(private val repository: PokemonRepository): ViewModel() {
    private val offset = 20
    private var totalPokemon = 0
    private val _pokemon = MutableLiveData<ArrayList<Pokemon>>().apply{
        value = ArrayList()
        repository.getPokemonList(totalPokemon) { pokemon ->
            value?.add(pokemon)
            value?.sort()
            value = value

            viewModelScope.launch {
                repository.savePokemon(pokemon)
            }
        }
        totalPokemon += offset
    }

    val pokemon: LiveData<ArrayList<Pokemon>>
        get() = _pokemon

    fun getNextPokemon() {
        if (totalPokemon == _pokemon.value?.count()) {
            _pokemon.apply {
                repository.getPokemonList(totalPokemon) { pokemon ->
                    value?.add(pokemon)
                    value?.sort()
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