package io.github.ziginsider.epam_laba14.cache

import android.graphics.Bitmap

/**
 * An implementation of image cache
 *
 * @author Alex Kisel
 * @since 2018-05-04
 */
class ImageCache(capacity: Int): LruCache<String, Bitmap>(capacity) {

    override fun getValueSize(value: Bitmap) = value.byteCount
}
