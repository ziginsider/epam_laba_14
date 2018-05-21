package io.github.ziginsider.epam_laba14.utils

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Extension for ViewGroup.
 *
 * @return Inflated View
 */
infix fun ViewGroup.inflate(layoutResId: Int) =
        LayoutInflater.from(context).inflate(layoutResId, this, false)

/**
 * Extension for Bitmap. Scales bitmap to new width and height
 */
fun Bitmap.resize(newWidth: Int, newHeight: Int)
        = Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
