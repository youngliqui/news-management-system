package ru.clevertec.core.cache;

public interface CacheManager<K, V> {
    V get(K key);

    void put(K key, V value);

    void remove(K key);
}
