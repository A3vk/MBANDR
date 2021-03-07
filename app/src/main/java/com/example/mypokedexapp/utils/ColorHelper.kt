package com.example.mypokedexapp.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils

class ColorHelper {
    companion object {
        fun darken(color: Int, value: Float): Int {
            val hsl = FloatArray(3)
            ColorUtils.colorToHSL(color, hsl)
            hsl[2] -= value
            hsl[2] = 0f.coerceAtLeast(hsl[2].coerceAtMost(1f))
            return ColorUtils.HSLToColor(hsl)
        }
    }
}