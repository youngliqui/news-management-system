package ru.clevertec.core.cache.factory;

import org.junit.jupiter.api.Test;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.cache.LFUCache;
import ru.clevertec.core.cache.LRUCache;

import static org.assertj.core.api.Assertions.assertThat;

class CacheManagerFactoryTest {
    @Test
    void testLFUFactoryCreatesLFU() {
        CacheManagerFactory factory = new LFUCacheFactory();

        CacheManager<Integer, String> cacheManager = factory.createCacheManager(10);

        assertThat(cacheManager instanceof LFUCache).isTrue();
    }

    @Test
    void testLRUFactoryCreatesLRU() {
        CacheManagerFactory factory = new LRUCacheFactory();

        CacheManager<Integer, String> cacheManager = factory.createCacheManager(10);

        assertThat(cacheManager instanceof LRUCache).isTrue();
    }
}