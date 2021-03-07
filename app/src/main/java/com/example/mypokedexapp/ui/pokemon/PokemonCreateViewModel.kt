package com.example.mypokedexapp.ui.pokemon

import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository
import kotlinx.coroutines.launch

class PokemonCreateViewModel(private val pokemonRepository: PokemonRepository): ViewModel() {
    val nextCustomPokemonId: LiveData<Int>
        get() = _nextCustomPokemonId

    private val _nextCustomPokemonId = MutableLiveData<Int>().apply{
        viewModelScope.launch {
            value = pokemonRepository.getNewCustomPokemonId()
            println("HALLO IK BEN HIER!!!!!!!")
        }
    }

    fun saveCustomPokemon(pokemon: Pokemon){
        viewModelScope.launch {
            pokemonRepository.savePokemon(pokemon)
        }
    }
}

class PokemonCreateViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonCreateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonCreateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}