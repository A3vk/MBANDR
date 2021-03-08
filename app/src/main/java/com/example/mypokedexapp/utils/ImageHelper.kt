package com.example.mypokedexapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageHelper {
    companion object {
        fun bitmapToBase64(bitmap: Bitmap): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        fun base64ToBitmap(string: String): Bitmap {
            val decodedString = Base64.decode(string.substring(string.indexOf(",") + 1), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }
    }
}