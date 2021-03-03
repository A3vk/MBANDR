package com.example.mypokedexapp.models

import org.json.JSONArray
import org.json.JSONObject

data class PokemonStats(var hp: Number, var attack: Number, var defence: Number, var specialAttack: Number, var specialDefence: Number, var speed: Number) {
    companion object {
        fun fromJson(json: JSONArray): PokemonStats {
            return PokemonStats(
                json.getJSONObject(0).getInt("base_stat"),
                json.getJSONObject(1).getInt("base_stat"),
                json.getJSONObject(2).getInt("base_stat"),
                json.getJSONObject(3).getInt("base_stat"),
                json.getJSONObject(4).getInt("base_stat"),
                json.getJSONObject(5).getInt("base_stat")
            )
        }
    }
}