package com.example.mypokedexapp.ui.profile

import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.models.Type
import com.example.mypokedexapp.repositories.PokemonRepository
import com.example.mypokedexapp.ui.pokemon.PokemonDetailViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    val customPokemon: LiveData<List<Pokemon>> = pokemonRepository.customPokemon.asLiveData()
    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun removeCustomPokemon(index: Int){
        val pokemon = customPokemon.value?.get(index)
        viewModelScope.launch {
            pokemonRepository.removeSavedPokemon(pokemon!!)
        }
    }
}

class ProfileViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
