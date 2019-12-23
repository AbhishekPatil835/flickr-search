package com.example.flickr.utils

import androidx.recyclerview.widget.RecyclerView


abstract class Divider : RecyclerView.ItemDecoration() {

    /**
     * Get the height of the divider.
     *
     * @return height of the divider.
     */
    abstract val height: Int

    /**
     * Get the width of the divider.
     *
     * @return width of the divider.
     */
    abstract val width: Int

}