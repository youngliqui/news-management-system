package ru.clevertec.clientapi.cache.factory;

import org.springframework.stereotype.Component;
import ru.clevertec.clientapi.cache.CacheManager;
import ru.clevertec.clientapi.cache.LRUCache;

@Component
public class LRUCacheFactory implements CacheManagerFactory {
    @Override
    public <K, V> CacheManager<K, V> createCacheManager(int maxSize) {
        return new LRUCache<>(maxSize);
    }
}
