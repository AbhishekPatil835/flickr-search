package com.example.flickr.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View


open class Drawer(
    private val mDivider: Drawable,
    private val mWidth: Int,
    private val mHeight: Int
) {

    /**
     * Draw the divider on the left side of the Item.
     */
    fun drawLeft(view: View, c: Canvas) {
        val left = view.getLeft() - mWidth
        val top = view.getTop() - mHeight
        val right = left + mWidth
        val bottom = view.getBottom() + mHeight
        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(c)
    }

    /**
     * Draw the divider on the top side of the Item.
     */
    fun drawTop(view: View, c: Canvas) {
        val left = view.getLeft() - mWidth
        val top = view.getTop() - mHeight
        val right = view.getRight() + mWidth
        val bottom = top + mHeight
        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(c)
    }

    /**
     * Draw the divider on the top side of the Item.
     */
    fun drawRight(view: View, c: Canvas) {
        val left = view.getRight()
        val top = view.getTop() - mHeight
        val right = left + mWidth
        val bottom = view.getBottom() + mHeight
        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(c)
    }

    /**
     * Draw the divider on the top side of the Item.
     */
    fun drawBottom(view: View, c: Canvas) {
        val left = view.getLeft() - mWidth
        val top = view.getBottom()
        val right = view.getRight() + mWidth
        val bottom = top + mHeight
        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(c)
    }
}