package io.github.ziginsider.epam_laba14

/**
 * A LRU Cache implementation
 *
 * @author Alex Kisel
 * @since 2018-05-03
 */
open class LruCache<in K, V>(capacity: Int = 10) : Cache<K, V> {

    private val lruMap: LruLinkedHashMap<K, V>
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

    override fun resize(maxSize: Int) {
        if (maxSize <= 0) {
            throw IllegalArgumentException("[ LRU Cache's capacity must be positive ]")
        }
        maxCacheSize = maxSize
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

    override fun getCurrentSize() = currentCacheSize

    override fun getMaxSize() = maxCacheSize

    protected open fun getValueSize(value: V) = 1024 * 1024
}