package com.example.mypokedexapp.repositories

import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.volley.RouterVolley
import com.example.mypokedexapp.volley.ServiceVolley

class PokemonRepository {
    private val service = ServiceVolley()

    fun getPokemon(completionHandler: (onComplete: ArrayList<Pokemon>) -> Unit){
        val pokemon = ArrayList<Pokemon>()
        service.get(RouterVolley.Pokemon.BASE) { response ->
            if (response != null) {
                val result = response.getJSONArray("results")
                for (index in 0 until result.length()) {
                    service.get(result.getJSONObject(index).getString("url")) { pokemonJson ->
                        if (pokemonJson != null) {
                            pokemon.add(Pokemon().fromJson(pokemonJson))
                            if(pokemon.count() == result.length()) {
                                completionHandler(pokemon)
                            }
                        }
                    }
                }
            }
        }
    }
}