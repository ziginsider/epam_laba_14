package io.github.ziginsider.epam_laba14

import android.graphics.Bitmap
import android.widget.ImageView
import io.github.ziginsider.epam_laba14.cache.ImageCache
import java.util.concurrent.Callable

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

    fun capacityCache(newCapacity: Int) {
        cache.setCapacity(newCapacity)
    }

    fun threadCount(newCount: Int) {
        threadCount = newCount
    }

    private inner class ImageDownloadTask(val url: String) : Callable<Bitmap> {

        override fun call(): Bitmap {
            return downloadImage(url)
        }

        private fun downloadImage(url: String): Bitmap {
            //TODO downloading image

            
        }
    }


}
