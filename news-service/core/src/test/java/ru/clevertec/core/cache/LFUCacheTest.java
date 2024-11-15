package ru.clevertec.core.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LFUCacheTest {
    private LFUCache<Integer, String> lfuCache;

    @BeforeEach
    void setUp() {
        lfuCache = new LFUCache<>(2);
    }

    @Test
    void testPutAndGet() {
        lfuCache.put(1, "One");
        lfuCache.put(2, "Two");

        var first = lfuCache.get(1);
        var second = lfuCache.get(2);

        assertThat(first).isEqualTo("One");
        assertThat(second).isEqualTo("Two");
    }

    @Test
    void testEviction() {
        lfuCache.put(1, "One");
        lfuCache.put(2, "Two");
        lfuCache.put(3, "Three");

        var first = lfuCache.get(1);
        var second = lfuCache.get(2);
        var third = lfuCache.get(3);

        assertThat(first).isNull();
        assertThat(second).isEqualTo("Two");
        assertThat(third).isEqualTo("Three");
    }

    @Test
    void testUpdateFrequency() {
        lfuCache.put(1, "One");
        lfuCache.put(2, "Two");
        lfuCache.get(1);
        lfuCache.put(3, "Three");

        var first = lfuCache.get(1);
        var second = lfuCache.get(2);
        var third = lfuCache.get(3);

        assertThat(first).isEqualTo("One");
        assertThat(second).isNull();
        assertThat(third).isEqualTo("Three");
    }

    @Test
    void testRemove() {
        lfuCache.put(1, "One");
        lfuCache.put(2, "Two");

        lfuCache.remove(1);

        var first = lfuCache.get(1);
        var second = lfuCache.get(2);

        assertThat(first).isNull();
        assertThat(second).isEqualTo("Two");
    }
}