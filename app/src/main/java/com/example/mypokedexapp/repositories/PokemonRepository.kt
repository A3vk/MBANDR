package com.example.mypokedexapp.repositories

import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.values.Endpoints
import com.example.mypokedexapp.volley.ServiceVolley

class PokemonRepository {
    private val service = ServiceVolley()

    fun getPokemon(offset: Int, completionHandler: (onComplete: Pokemon) -> Unit){
        val pokemon = ArrayList<Pokemon>()
        service.get("${RouterVolley.Pokemon.BASE}?offset=$offset&limit=20") { response ->
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
}