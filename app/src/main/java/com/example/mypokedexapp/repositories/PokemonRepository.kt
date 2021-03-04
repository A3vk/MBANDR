package com.example.mypokedexapp.repositories

import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.volley.RouterVolley
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
}