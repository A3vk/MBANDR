package com.example.mypokedexapp.utils

import android.graphics.Color
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.green

class ColorHelper {
    companion object {
        fun darken(color: Int, value: Float): Int {
            val hsl = FloatArray(3)
            ColorUtils.colorToHSL(color, hsl)
            hsl[2] -= value
            hsl[2] = 0f.coerceAtLeast(hsl[2].coerceAtMost(1f))
            return ColorUtils.HSLToColor(hsl)
        }

        private fun limitColorValue(colorValue: Int): Int {
            return if (colorValue in 0..255) colorValue else 0
        }

        fun rgbToHex(red: Int, green: Int, blue: Int): String {
            return String.format("%02X%02X%02X", limitColorValue(red), limitColorValue(green), limitColorValue(blue))
        }

        fun colorToHex(color: Int): String {
            return rgbToHex(Color.red(color), Color.green(color), Color.blue(color))
        }
    }
}