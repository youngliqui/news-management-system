package ru.clevertec.core.cache.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.core.cache.CacheAlgorithm;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.cache.factory.CacheManagerFactory;
import ru.clevertec.core.cache.factory.LFUCacheFactory;
import ru.clevertec.core.cache.factory.LRUCacheFactory;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;

/**
 * Конфигурация кэша для приложения.
 * <p>
 * Этот класс настраивает менеджеры кэша для сущностей новостей и комментариев,
 * используя указанный алгоритм кэширования (LFU или LRU) и максимальный размер кэша.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
@Getter
@Setter
public class CacheConfig {
    @Value("${cache.algorithm}")
    private CacheAlgorithm algorithm;

    @Value("${cache.maxSize}")
    private int maxSize;

    private final ApplicationContext applicationContext;

    @Bean
    public CacheManager<Long, NewsEntity> newsCacheManager() {
        return createCacheManager();
    }

    @Bean
    public CacheManager<Long, CommentEntity> commentCacheManager() {
        return createCacheManager();
    }


    private <T> CacheManager<Long, T> createCacheManager() {
        CacheManagerFactory factory = getCacheFactory();
        return factory.createCacheManager(maxSize);
    }

    private CacheManagerFactory getCacheFactory() {
        return switch (algorithm) {
            case LFU -> applicationContext.getBean(LFUCacheFactory.class);
            case LRU -> applicationContext.getBean(LRUCacheFactory.class);
        };
    }

}
