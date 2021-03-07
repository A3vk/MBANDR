package com.example.mypokedexapp.volley

import android.app.Application
import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ServiceVolley(private val context: Context) : IServiceVolley {
    override fun get(endpoint: String, query: String, completionHandler: (response: JSONObject?) -> Unit) {
        val url = "${endpoint}${query}/"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                completionHandler(response)
            },
            { error ->
                // TODO: Handle error
                completionHandler(null)
            }
        )
        BackendVolley.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }
}