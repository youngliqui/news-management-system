package ru.clevertec.clientapi.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> implements CacheManager<K, V> {
    private final Map<K, V> cache;

    public LRUCache(int maxSize) {
        this.cache = new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }
}
