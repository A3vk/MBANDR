package com.example.mypokedexapp.ui.team

import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository
import com.example.mypokedexapp.ui.profile.ProfileViewModel
import kotlinx.coroutines.launch

class TeamViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    val pokemonTeam: LiveData<List<Pokemon>> = pokemonRepository.pokemonTeam.asLiveData()

    fun removePokemonFromTeam(index: Int) {
        val pokemon = pokemonTeam.value?.get(index)
        viewModelScope.launch {
            pokemonRepository.removeFromTeam(pokemon!!)
        }
    }
}

class TeamViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}