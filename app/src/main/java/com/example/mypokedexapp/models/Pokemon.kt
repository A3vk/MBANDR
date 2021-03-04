package com.example.mypokedexapp.models

import org.json.JSONObject

data class Pokemon(var id: Int, var name: String, var imageUrl: String, var stats : ArrayList<Stat>, var types: ArrayList<Type>) : Comparable<Pokemon> {
    companion object {
        fun fromJson(json: JSONObject): Pokemon {
            val jsonStats = json.getJSONArray("stats")
            val stats = ArrayList<Stat>()
            for(index in 0 until jsonStats.length()) {
                stats.add(Stat.fromJson(jsonStats.getJSONObject(index)))
            }

            val jsonTypes = json.getJSONArray("types")
            val types = ArrayList<Type>()
            for(index in 0 until jsonTypes.length()) {
                types.add(Type.fromJson(jsonTypes.getJSONObject(index)))
            }

            return Pokemon(
                json.getInt("id"),
                json.getString("name"),
                json.getJSONObject("sprites").getString("front_default"),
                stats,
                types
            )
        }
    }

    override fun compareTo(other: Pokemon): Int {
        return id - other.id
    }
}