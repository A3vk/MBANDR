package com.example.mypokedexapp.volley

import org.json.JSONObject

interface IServiceVolley {
    fun get(endpoint: String, query: String = "", completionHandler: (response: JSONObject?) -> Unit)
}