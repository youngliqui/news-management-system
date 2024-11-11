package ru.clevertec.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация кэша с наименьшей частотой использования (LFU).
 * <p>
 * Этот класс сохраняет элементы в кэше и удаляет наименее часто используемые элементы,
 * когда достигается максимальный размер кэша.
 * </p>
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class LFUCache<K, V> implements CacheManager<K, V> {
    private final int maxSize;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequencyMap;

    public LFUCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }

    /**
     * Получает значение из кэша по заданному ключу.
     *
     * @param key ключ, по которому нужно получить значение
     * @return значение, соответствующее заданному ключу, или null, если ключ не найден
     */
    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        frequencyMap.put(key, frequencyMap.get(key) + 1);

        return cache.get(key);
    }

    /**
     * Добавляет значение в кэш с заданным ключом.
     *
     * @param key   ключ, под которым будет храниться значение
     * @param value значение, которое нужно сохранить в кэше
     */
    @Override
    public void put(K key, V value) {
        if (cache.size() >= maxSize) {
            removeLeastFrequentlyUsed();
        }
        cache.put(key, value);
        frequencyMap.put(key, 1);
    }

    /**
     * Удаляет значение из кэша по заданному ключу.
     *
     * @param key ключ, по которому нужно удалить значение
     */
    @Override
    public void remove(K key) {
        cache.remove(key);
        frequencyMap.remove(key);
    }

    /**
     * Удаляет наименее часто используемый элемент из кэша.
     */
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
