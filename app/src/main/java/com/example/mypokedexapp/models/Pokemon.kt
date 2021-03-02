package com.example.mypokedexapp.models

import org.json.JSONObject

class Pokemon {
    lateinit var id: Number
    lateinit var name: String
    lateinit var imageUrl: String

    fun fromJson(json: JSONObject): Pokemon {
        id = json.getInt("id")
        name = json.getString("name")
        imageUrl = json.getJSONObject("sprites").getString("front_default")
        return this
    }
}