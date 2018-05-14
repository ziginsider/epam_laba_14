package io.github.ziginsider.epam_laba14

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
     * @param maxSize new size of the cache
     */
    fun resize(maxSize: Int)

    /**
     * Returns the current memory size of the cache
     *
     * @return current memory size of the cache
     */
    fun getCurrentSize(): Int

    /**
     * Returns the max memory size of the cache
     *
     * @return max memory size of the cache
     */
    fun getMaxSize(): Int
}