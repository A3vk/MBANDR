package com.example.mypokedexapp.ui.profile

import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.models.Type
import com.example.mypokedexapp.repositories.PokemonRepository
import com.example.mypokedexapp.ui.pokemon.PokemonDetailViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: PokemonRepository) : ViewModel() {
    val customPokemon: LiveData<List<Pokemon>> = repository.customPokemon.asLiveData()
    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun addPokemon() = viewModelScope.launch {
        val pokemon = Pokemon(-1, "Carl", "https://ucarecdn.com/68712676-976b-4966-92d8-b16694fcf261/anime-fans-nederland-amelia.png", 5, 5, 5, 5, 5, 5, Type("fire", "#EE8130"),   Type("dark", "#705746"), false)
        repository.savePokemon(pokemon)
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
