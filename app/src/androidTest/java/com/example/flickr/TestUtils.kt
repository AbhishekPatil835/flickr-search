package com.example.flickr

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import com.example.flickr.data.Photo
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

val photo = Photo("49242902122","22539273@N00","c5b6c48cf5","65535",66,"Car park @ Secteur du Berger @ Semnoz @ Annecy",1,0,0)
val photoList = arrayListOf(photo, photo, photo, photo , photo)

val emptyList = arrayListOf<Photo>()

fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("ImageView with drawable same as drawable with id $id")
    }

    override fun matchesSafely(view: View): Boolean {
        val context = view.context
        val expectedBitmap = context.getDrawable(id)?.toBitmap()

        return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
    }
}