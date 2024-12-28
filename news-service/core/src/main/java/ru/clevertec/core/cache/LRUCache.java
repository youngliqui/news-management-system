package ru.clevertec.core.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Реализация кэша с наименьшим временем использования (LRU).
 * <p>
 * Этот класс сохраняет элементы в кэше и удаляет наименее недавно использованные элементы,
 * когда достигается максимальный размер кэша.
 * </p>
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class LRUCache<K, V> implements CacheManager<K, V> {
    private final Map<K, V> cache;

    public LRUCache(int maxSize) {
        this.cache = new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
    }

    /**
     * Получает значение из кэша по заданному ключу.
     *
     * @param key ключ, по которому нужно получить значение
     * @return значение, соответствующее заданному ключу или null если ключ не найден
     */
    @Override
    public V get(K key) {
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
        cache.put(key, value);
    }

    /**
     * Удаляет значение из кэша по заданному ключу.
     *
     * @param key ключ, по которому нужно удалить значение
     */
    @Override
    public void remove(K key) {
        cache.remove(key);
    }
}
