package com.example.mypokedexapp.volley

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class BackendVolley(context: Context) {
    // Singelton code
    companion object {
        @Volatile
        private var INSTANCE: BackendVolley? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BackendVolley(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue, object: ImageLoader.ImageCache {
            private val cache = LruCache<String, Bitmap>(1024)
            override fun getBitmap(url: String): Bitmap? {
                return cache.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                cache.put(url, bitmap)
            }
        })
    }
}