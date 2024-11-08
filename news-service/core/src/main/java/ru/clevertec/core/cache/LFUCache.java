package ru.clevertec.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        Optional<K> leastUsedKey = frequencyMap.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        leastUsedKey.ifPresent(key -> {
            cache.remove(key);
            frequencyMap.remove(key);
        });
    }
}
