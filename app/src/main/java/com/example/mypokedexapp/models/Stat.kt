package com.example.mypokedexapp.models

import org.json.JSONArray
import org.json.JSONObject

data class Stat(var name: String, var value: Int) {
    companion object {
        fun fromJson(json: JSONObject): Stat {
            return Stat(
                json.getJSONObject("stat").getString("name"),
                json.getInt("base_stat")
            )
        }
    }
}