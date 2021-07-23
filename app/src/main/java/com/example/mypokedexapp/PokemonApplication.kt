package com.example.mypokedexapp

import android.app.Application
import com.example.mypokedexapp.repositories.PokemonRepository
import com.example.mypokedexapp.room.PokemonDatabase
import com.example.mypokedexapp.volley.BackendVolley
import com.example.mypokedexapp.volley.ServiceVolley

class PokemonApplication: Application() {
    private val database by lazy { PokemonDatabase.getInstance(this) }
    private val service by lazy { ServiceVolley(this) }
    val imageLoader by lazy { BackendVolley.getInstance(this).imageLoader }
    val repository by lazy { PokemonRepository(database.pokemonDao(), service) }
}