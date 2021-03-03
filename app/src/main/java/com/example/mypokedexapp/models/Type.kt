package com.example.mypokedexapp.models

import org.json.JSONObject

data class Type(var name: String, var color: String) {
    companion object {
        fun fromJson(json: JSONObject): Type {
            return Type(
                json.getJSONObject("type").getString("name"),
                ""
            )
        }
    }
}