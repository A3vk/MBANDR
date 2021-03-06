package com.example.mypokedexapp.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.room.PokemonDatabase
import com.example.mypokedexapp.room.dao.PokemonDao
import com.example.mypokedexapp.values.Endpoints
import com.example.mypokedexapp.volley.ServiceVolley

class PokemonRepository(private val pokemonDao: PokemonDao) {
    private val service = ServiceVolley()

    fun getPokemonList(offset: Int, completionHandler: (onComplete: Pokemon) -> Unit){
        val pokemon = ArrayList<Pokemon>()
        service.get("${Endpoints.Pokemon.BASE}?offset=$offset&limit=20") { response ->
            if (response != null) {
                val result = response.getJSONArray("results")
                for (index in 0 until result.length()) {
                    service.get(result.getJSONObject(index).getString("url")) { pokemonJson ->
                        if (pokemonJson != null) {
                            completionHandler(Pokemon.fromJson(pokemonJson) )
                        }
                    }
                }
            }
        }
    }

    fun getPokemon(id: Number, completionHandler: (onComplete: Pokemon) -> Unit){
        service.get("${Endpoints.Pokemon.BASE}$id") { response ->
            if (response != null) {
                completionHandler(Pokemon.fromJson(response))
            }
        }
    }

    @WorkerThread
    suspend fun savePokemon(pokemon: Pokemon) {
        pokemonDao.insertPokemon(pokemon)
    }

    fun getSavedPokemon(number: Int): Pokemon? {
        return pokemonDao.getPokemon(number)
    }
}