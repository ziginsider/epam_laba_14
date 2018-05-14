package io.github.ziginsider.epam_laba14

/**
 * LinkedHashMap implementation for LRU cache (accessOrder = true)
 *
 * @author Alex Kisel
 * @since 2018-05-03
 */
class LruLinkedHashMap<K, V>(private val capacity: Int)
    : LinkedHashMap<K, V>(capacity, 0.75F, true) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        return size > capacity
    }
}