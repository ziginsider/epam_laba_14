package io.github.ziginsider.epam_laba14.cache

import io.github.ziginsider.epam_laba14.utils.logi

/**
 * LinkedHashMap implementation for LRU cache (accessOrder = true)
 *
 * @author Alex Kisel
 * @since 2018-05-03
 */
class LruLinkedHashMap<K, V>(private val capacity: Int)
    : LinkedHashMap<K, V>(capacity, 0.75F, true) {

    private val TAG = LruLinkedHashMap::class.java.simpleName

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        logi(TAG, "[ removeEldestEntry() ]")
        return size > capacity
    }
}
