package io.github.ziginsider.epam_laba14

import android.widget.ImageView
import io.github.ziginsider.epam_laba14.cache.ImageCache

object ImageLoader {

    //TODO set capacity?
    private val cache = ImageCache(10)

    private var threadCount: Int? = null

    fun displayImage(view: ImageView, url: String) {
        synchronized(cache) {
            val bitmap = cache.get(url)
            if (bitmap == null) {
                //TODO start downloading... value = ...
                cache.put(url, value)
            } else {
                //TODO thread??
                view.setImageBitmap(bitmap)
            }
        }
    }

    fun sizeCache(newSize: Int) {
        cache.resize(newSize)
    }

    fun threadCount(newCount: Int) {
        threadCount = newCount
    }

    fun capacityCache(newCapacity: Int) {
        cache.setCapacity(newCapacity)
    }

}
