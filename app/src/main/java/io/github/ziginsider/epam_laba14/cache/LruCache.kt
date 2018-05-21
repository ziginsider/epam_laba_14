package io.github.ziginsider.epam_laba14.cache

import io.github.ziginsider.epam_laba14.utils.logi

/**
 * An implementation of LRU cache
 *
 * @author Alex Kisel
 * @since 2018-05-03
 */
open class LruCache<in K, V>(private var capacity: Int = 10) : Cache<K, V> {

    private val TAG = LruCache::class.java.simpleName

    private var lruMap: LruLinkedHashMap<K, V>
    private var currentCacheSize: Int
    private var maxCacheSize: Int

    init {
        if (capacity <= 0) {
            throw IllegalArgumentException("[ LRU Cache's capacity must be positive ]")
        }
        lruMap = LruLinkedHashMap(capacity)
        currentCacheSize = 0
        maxCacheSize = capacity * 1024 * 1024
        logi(TAG, "[ init: capacity = $capacity, currentCacheSize = $currentCacheSize, " +
                "maxCacheSize = $maxCacheSize ]")
    }

    override fun put(key: K, value: V) = if (lruMap.put(key, value) == null) {
        currentCacheSize += getValueSize(value)
        if (currentCacheSize > maxCacheSize) {
            logi(TAG, "[ put(): currentCacheSize ($currentCacheSize) > " +
                    "maxCacheSize ($maxCacheSize)]")
            val oldestValue = lruMap.entries.iterator().next()
            lruMap.remove(oldestValue.key)
            currentCacheSize -= getValueSize(oldestValue.value)
        }
        logi(TAG, "[ put($key, $value): successful ]")
        true
    } else {
        logi(TAG, "[ put($key, $value): unsuccessful ]")
        false
    }

    override fun get(key: K): V? {
        logi(TAG, "[ get($key) ]")
        return lruMap[key]
    }

    override fun resize(newSize: Int) {
        logi(TAG, "[ resize($newSize) ]")
        if (newSize <= 0) {
            throw IllegalArgumentException("[ LRU Cache's size must be positive ]")
        }
        maxCacheSize = newSize
        while (true) {
            if (currentCacheSize <= maxCacheSize || lruMap.isEmpty()) {
                break
            }
            logi(TAG, "[ put(): currentCacheSize ($currentCacheSize) > " +
                    "maxCacheSize ($maxCacheSize)]")
            val oldestValue = lruMap.entries.iterator().next()
            lruMap.remove(oldestValue.key)
            currentCacheSize -= getValueSize(oldestValue.value)
        }
    }

    override fun setCapacity(newCapacity: Int) {
        logi(TAG, "[ setCapacity($newCapacity) ]")
        val tempLruMap = lruMap
        lruMap = LruLinkedHashMap(newCapacity)
        maxCacheSize = newCapacity * 1024 * 1024
        if (tempLruMap.size > 0) {
            lruMap.putAll(tempLruMap)
        }
    }

    override fun getCurrentSize() = currentCacheSize

    override fun getMaxSize() = maxCacheSize

    override fun getCapacity() = capacity

    /**
     * Gets [value] size in bytes
     */
    protected open fun getValueSize(value: V) = 1024 * 1024
}
