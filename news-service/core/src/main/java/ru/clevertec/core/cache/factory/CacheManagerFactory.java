package ru.clevertec.core.cache.factory;

import ru.clevertec.core.cache.CacheManager;

public interface CacheManagerFactory {
    <K, V> CacheManager<K, V> createCacheManager(int maxSize);
}
