package com.example.mypokedexapp.volley

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class BackendVolley : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private val TAG = BackendVolley::class.java.simpleName
        @get:Synchronized var instance: BackendVolley? = null
            private set
    }

    private  val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue, object: ImageLoader.ImageCache {
            private val cache = LruCache<String, Bitmap>(20)
            override fun getBitmap(url: String): Bitmap {
                return cache.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                cache.put(url, bitmap)
            }
        })
    }

}