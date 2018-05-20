package io.github.ziginsider.epam_laba14.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.ziginsider.epam_laba14.model.Photo
import kotlinx.android.extensions.LayoutContainer

class RecyclerViewAdapter(private val layoutResId: Int, private val clickListener: (Photo) -> Unit)
    : ListAdapter<Photo, RecyclerViewAdapter.ViewHolder>(PhotoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): {

    }

    override fun onBindViewHolder(holder:, position: Int) {

    }

    class ViewHolder(override val containerView: View?)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(photo: Photo, clickListener: (Photo) -> Unit) {


        }
    }

}