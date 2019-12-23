package com.example.flickr.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import android.graphics.drawable.ColorDrawable


internal class ColorDrawer(color: Int, width: Int, height: Int) :
    Drawer(ColorDrawable(opaqueColor(color)), width, height) {
    companion object {

        /**
         * The target color is packaged in an opaque color.
         *
         * @param color color.
         * @return color.
         */
        @ColorInt
        fun opaqueColor(@ColorInt color: Int): Int {
            val alpha = Color.alpha(color)
            if (alpha == 0) return color
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)
            return Color.argb(255, red, green, blue)
        }
    }
}