package com.example.flickr.utils.views

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView


class SquareCardView @JvmOverloads constructor(
    context: Context, @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val mConfig: Configuration

    init {
        this.mConfig = resources.configuration
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val orientation = mConfig.orientation
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec)
            }
            else -> {
                throw AssertionError("This should not be the case.")
            }
        }
    }
}