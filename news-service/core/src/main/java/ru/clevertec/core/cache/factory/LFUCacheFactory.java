package ru.clevertec.core.cache.factory;

import org.springframework.stereotype.Component;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.cache.LFUCache;

@Component
public class LFUCacheFactory implements CacheManagerFactory {
    @Override
    public <K, V> CacheManager<K, V> createCacheManager(int maxSize) {
        return new LFUCache<>(maxSize);
    }
}
