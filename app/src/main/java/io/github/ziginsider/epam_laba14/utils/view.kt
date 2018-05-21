package io.github.ziginsider.epam_laba14.utils

import android.content.res.Resources
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
 * Extension for Int. Converts [Int] to dp
 */
val Int.asDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()