package com.example.mypokedexapp.volley

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ServiceVolley() : IServiceVolley {
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
        BackendVolley.instance?.addToRequestQueue(jsonObjectRequest)
    }
}