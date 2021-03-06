package io.github.ziginsider.epam_laba14.cache

/**
 * A LRU Cache interface
 *
 * @author Alex Kisel
 * @since 2018-05-03
 */
interface Cache<in K, V> {

    /**
     * Puts an value in the cache for [key]
     *
     * @param key key (url)
     * @param value value (image)
     * @return true if operation was successful, false - value for specified key already exists
     */
    fun put(key: K, value: V): Boolean

    /**
     * Get the value for the [key]
     *
     * @param key key (url)
     * @return value (image) for the specified [key] or null
     */
    fun get(key: K): V?

    /**
     * Sets the size of the cache
     *
     * @param newSize new size of the cache in bytes
     */
    fun resize(newSize: Int)

    /**
     * Sets the capacity (number of elements) of the cache
     *
     * @param newCapacity new capacity of the cache
     */
    fun setCapacity(newCapacity: Int)
}
