package com.example.mypokedexapp.repositories

import com.example.mypokedexapp.volley.RouterVolley
import com.example.mypokedexapp.volley.ServiceVolley

class PokemonRepository {
    private val service = ServiceVolley()

    fun getPokemon() {
        service.get(RouterVolley.Pokemon.BASE) { response ->
            println(response)
        }
    }
}