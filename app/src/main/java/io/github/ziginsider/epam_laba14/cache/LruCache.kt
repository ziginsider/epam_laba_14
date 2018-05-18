package io.github.ziginsider.epam_laba14.cache

/**
 * An implementation of LRU cache
 *
 * @author Alex Kisel
 * @since 2018-05-03
 */
open class LruCache<in K, V>(private var capacity: Int = 10) : Cache<K, V> {

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
    }

    override fun put(key: K, value: V) = if (lruMap.put(key, value) == null) {
        currentCacheSize += getValueSize(value)
        if (currentCacheSize > maxCacheSize) {
            val oldestValue = lruMap.entries.iterator().next()
            //TODO val oldestValue = lruMap.entries.first() ??
            lruMap.remove(oldestValue.key)
            currentCacheSize -= getValueSize(oldestValue.value)
        }
        true
    } else {
        false
    }

    override fun get(key: K) = lruMap[key]

    override fun resize(newSize: Int) {
        if (newSize <= 0) {
            throw IllegalArgumentException("[ LRU Cache's size must be positive ]")
        }
        maxCacheSize = newSize
        while (true) {
            if (currentCacheSize <= maxCacheSize || lruMap.isEmpty()) {
                break
            }
            val oldestValue = lruMap.entries.iterator().next()
            //TODO val oldestValue = lruMap.entries.first() ??
            lruMap.remove(oldestValue.key)
            currentCacheSize -= getValueSize(oldestValue.value)
        }
    }

    override fun setCapacity(newCapacity: Int) {
        val tempLruMap = lruMap
        lruMap = LruLinkedHashMap(newCapacity)
        maxCacheSize = newCapacity * 1024 * 1024
        if (tempLruMap.size > 0) {
            lruMap.putAll(tempLruMap)
        }
    }

    override fun getCurrentSize() = currentCacheSize

    override fun getMaxSize() = maxCacheSize

    /**
     * Gets [value] size in bytes
     */
    protected open fun getValueSize(value: V) = 1024 * 1024
}
