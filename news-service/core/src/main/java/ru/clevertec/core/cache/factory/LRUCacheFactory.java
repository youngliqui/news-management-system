package ru.clevertec.core.cache.factory;

import org.springframework.stereotype.Component;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.cache.LRUCache;

@Component
public class LRUCacheFactory implements CacheManagerFactory {
    @Override
    public <K, V> CacheManager<K, V> createCacheManager(int maxSize) {
        return new LRUCache<>(maxSize);
    }
}
