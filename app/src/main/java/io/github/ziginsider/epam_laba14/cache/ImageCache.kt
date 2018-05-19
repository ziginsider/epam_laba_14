package io.github.ziginsider.epam_laba14.cache

import android.graphics.Bitmap
import io.github.ziginsider.epam_laba14.utils.logi

/**
 * An implementation of image cache
 *
 * @author Alex Kisel
 * @since 2018-05-04
 */
class ImageCache(capacity: Int): LruCache<String, Bitmap>(capacity) {

    private val TAG = ImageCache::class.java.simpleName

    override fun getValueSize(value: Bitmap): Int {
        logi(TAG, "[ getValueSize($value) ]")
        return value.byteCount
    }
}
