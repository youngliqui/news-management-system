package ru.clevertec.clientapi.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.clientapi.cache.factory.CacheManagerFactory;
import ru.clevertec.clientapi.cache.factory.LFUCacheFactory;
import ru.clevertec.clientapi.cache.factory.LRUCacheFactory;
import ru.clevertec.clientapi.entity.CommentEntity;
import ru.clevertec.clientapi.entity.NewsEntity;

@Configuration
@RequiredArgsConstructor
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
