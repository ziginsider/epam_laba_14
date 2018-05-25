package io.github.ziginsider.epam_laba14.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.ziginsider.epam_laba14.ImageLoader
import io.github.ziginsider.epam_laba14.model.Photo
import io.github.ziginsider.epam_laba14.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_view.*

/**
 * Adapter for list of photos [Photo]
 *
 * Uses [PhotoDiffCallback] for renew list of items
 *
 * @author Alex Kisel
 * @since 2018-05-15
 */
class RecyclerViewAdapter(private val layoutResId: Int, private val clickListener: (Photo) -> Unit)
    : ListAdapter<Photo, RecyclerViewAdapter.ViewHolder>(PhotoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent inflate layoutResId
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(override val containerView: View?)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(photo: Photo, clickListener: (Photo) -> Unit) {
            with(photo) {
                //imagePhoto.setImageBitmap(null)
                titlePhoto.text = title
                if (urlSmall != null) {
                    ImageLoader.displayImage(imagePhoto, urlSmall)
                } else if (urlOriginal != null) {
                    ImageLoader.displayImage(imagePhoto, urlOriginal)
                }
                itemView.setOnClickListener { clickListener(this) }
            }
        }
    }
}
