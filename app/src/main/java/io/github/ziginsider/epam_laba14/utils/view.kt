package io.github.ziginsider.epam_laba14.utils

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Extension for ViewGroup.
 *
 * @return Inflated View
 */
infix fun ViewGroup.inflate(layoutResId: Int) =
        LayoutInflater.from(context).inflate(layoutResId, this, false)