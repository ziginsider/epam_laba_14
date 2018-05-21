package io.github.ziginsider.epam_laba14.adapter

import android.support.v7.util.DiffUtil
import io.github.ziginsider.epam_laba14.model.Photo

/**
 * Implements class that can calculate the difference between two lists and output a list of
 * update operations that converts the first list into the second one. It can be used to calculate
 * updates for a [RecyclerViewAdapter].
 *
 * @author Alex Kisel
 * @since 2018-05-15
 */
class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo?, newItem: Photo?) = oldItem?.id == newItem?.id

    override fun areContentsTheSame(oldItem: Photo?, newItem: Photo?) = oldItem == newItem
}
