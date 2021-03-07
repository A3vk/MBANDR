package com.example.mypokedexapp.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.room.PokemonDatabase
import com.example.mypokedexapp.room.dao.PokemonDao
import com.example.mypokedexapp.values.Endpoints
import com.example.mypokedexapp.volley.ServiceVolley
import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val pokemonDao: PokemonDao, private val serviceVolley: ServiceVolley) {
    // Pokemon API
    fun getPokemonList(offset: Int, completionHandler: (onComplete: Pokemon) -> Unit){
        serviceVolley.get("${Endpoints.Pokemon.BASE}?offset=$offset&limit=20") { response ->
            if (response != null) {
                val result = response.getJSONArray("results")
                for (index in 0 until result.length()) {
                    serviceVolley.get(result.getJSONObject(index).getString("url")) { pokemonJson ->
                        if (pokemonJson != null) {
                            completionHandler(Pokemon.fromJson(pokemonJson) )
                        }
                    }
                }
            }
        }
    }

    fun getPokemon(id: Number, completionHandler: (onComplete: Pokemon) -> Unit){
        serviceVolley.get("${Endpoints.Pokemon.BASE}$id") { response ->
            if (response != null) {
                completionHandler(Pokemon.fromJson(response))
            }
        }
    }

    // Local room database
    val pokemonTeam: Flow<List<Pokemon>> = pokemonDao.getPokemonTeam()
    val pokemonInTeam: Flow<Int> = pokemonDao.getNumberOfPokemonInTeam()
    val customPokemon: Flow<List<Pokemon>> = pokemonDao.getAllCustomPokemon()

    @WorkerThread
    suspend fun savePokemon(pokemon: Pokemon) {
        pokemonDao.insertPokemon(pokemon)
    }

    @WorkerThread
    suspend fun getSavedPokemon(number: Int): Pokemon {
        return pokemonDao.getPokemon(number)
    }

    @WorkerThread
    suspend fun removeSavedPokemon(pokemon: Pokemon){
        pokemonDao.deletePokemon(pokemon)
    }

    @WorkerThread
    suspend fun removeFromTeam(pokemon: Pokemon) {
        if(pokemon.number < 0) {
            pokemon.isInTeam = false
            pokemonDao.updatePokemon(pokemon)
        } else {
            pokemonDao.deletePokemon(pokemon)
        }
    }
}