package com.example.mypokedexapp.models

import org.json.JSONObject

data class Pokemon(var id: Int, var name: String, var imageUrl: String, var stats : PokemonStats) : Comparable<Pokemon> {
    companion object {
        fun fromJson(json: JSONObject): Pokemon {
            return Pokemon(
                json.getInt("id"),
                json.getString("name"),
                json.getJSONObject("sprites").getString("front_default"),
                PokemonStats.fromJson(json.getJSONArray("stats"))
            )
        }
    }

    override fun compareTo(other: Pokemon): Int {
        return id - other.id
    }
}