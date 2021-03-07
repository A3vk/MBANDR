package com.example.mypokedexapp.ui.pokemon

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.repositories.PokemonRepository
import com.example.mypokedexapp.ui.pokedex.PokedexViewModel
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _pokemon = MutableLiveData<Pokemon>()

    fun setPokemon(id: Int) {
        _pokemon.apply {
            pokemonRepository.getPokemon(id) { pokemon ->
                value = pokemon
            }
        }


    }

    var numberOfPokemonInTeam = 0
    init {
        // FIXME: Observe forever will always exist, even if the fragment is no longer visible. Find another way to do this.
        pokemonRepository.pokemonInTeam.asLiveData().observeForever {
            numberOfPokemonInTeam = it
        }
    }

    fun addPokemonToTeam() = viewModelScope.launch {
        _pokemon.value?.let {
            it.isInTeam = true
            pokemonRepository.savePokemon(it)
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