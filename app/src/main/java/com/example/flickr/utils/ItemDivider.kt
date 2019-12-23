package com.example.flickr.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.ColorInt


class ItemDivider
/**
 * @param color  line color.
 * @param width  line width.
 * @param height line height.
 */
@JvmOverloads constructor(@ColorInt color: Int, width: Int = 4, height: Int = 4) : Divider() {

    override val width: Int
    override val height: Int
    private val mDrawer: Drawer

    init {
        this.width = Math.round(width / 2f)
        this.height = Math.round(height / 2f)
        this.mDrawer = ColorDrawer(color, this.width, this.height)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(width, height, width, height)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()
        val layoutManager = parent.layoutManager
        val childCount = layoutManager!!.childCount
        for (i in 0 until childCount) {
            val view = layoutManager.getChildAt(i)
            view?.let {
                mDrawer.drawLeft(it, canvas)
                mDrawer.drawTop(it, canvas)
                mDrawer.drawRight(it, canvas)
                mDrawer.drawBottom(it, canvas)
            }

        }
        canvas.restore()
    }
}
/**
 * @param color divider line color.
 */