package ru.clevertec.core.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {
    private LRUCache<Integer, String> lruCache;

    @BeforeEach
    void setUp() {
        lruCache = new LRUCache<>(2);
    }

    @Test
    void testPutAndGet() {
        lruCache.put(1, "One");
        lruCache.put(2, "Two");

        var first = lruCache.get(1);
        var second = lruCache.get(2);

        assertThat(first).isEqualTo("One");
        assertThat(second).isEqualTo("Two");
    }

    @Test
    void testEviction() {
        lruCache.put(1, "One");
        lruCache.put(2, "Two");
        lruCache.put(3, "Three");

        var first = lruCache.get(1);
        var second = lruCache.get(2);
        var third = lruCache.get(3);

        assertThat(first).isNull();
        assertThat(second).isEqualTo("Two");
        assertThat(third).isEqualTo("Three");
    }

    @Test
    void testRemove() {
        lruCache.put(1, "One");
        lruCache.put(2, "Two");

        lruCache.remove(1);

        var first = lruCache.get(1);
        var second = lruCache.get(2);

        assertThat(first).isNull();
        assertThat(second).isEqualTo("Two");
    }
}