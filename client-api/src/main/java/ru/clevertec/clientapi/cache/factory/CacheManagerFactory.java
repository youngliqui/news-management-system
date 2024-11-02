package ru.clevertec.clientapi.cache.factory;

import ru.clevertec.clientapi.cache.CacheManager;

public interface CacheManagerFactory {
    <K, V> CacheManager<K, V> createCacheManager(int maxSize);
}
