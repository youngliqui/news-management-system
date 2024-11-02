package ru.clevertec.clientapi.cache;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> implements CacheManager<K, V> {
    private final int maxSize;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequencyMap;

    public LFUCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        frequencyMap.put(key, frequencyMap.get(key) + 1);

        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        if (cache.size() >= maxSize) {
            removeLeastFrequentlyUsed();
        }
        cache.put(key, value);
        frequencyMap.put(key, 1);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
        frequencyMap.remove(key);
    }


    private void removeLeastFrequentlyUsed() {
        K leastUsedKey = null;
        int minFrequency = Integer.MAX_VALUE;

        for (K key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            if (frequency < minFrequency) {
                minFrequency = frequency;
                leastUsedKey = key;
            }
        }

        if (leastUsedKey != null) {
            cache.remove(leastUsedKey);
            frequencyMap.remove(leastUsedKey);
        }
    }
}
