package ru.clevertec.core.cache.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import ru.clevertec.core.cache.CacheAlgorithm;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.cache.factory.LFUCacheFactory;
import ru.clevertec.core.cache.factory.LRUCacheFactory;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheConfigTest {
    @InjectMocks
    private CacheConfig cacheConfig;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private LFUCacheFactory lfuCacheFactory;

    @Mock
    private LRUCacheFactory lruCacheFactory;

    @BeforeEach
    void setUp() {
        cacheConfig = new CacheConfig(applicationContext);
        cacheConfig.setMaxSize(10);
    }

    @Test
    void testCreateNewsCacheManagerLFU() {
        cacheConfig.setAlgorithm(CacheAlgorithm.LFU);
        when(applicationContext.getBean(LFUCacheFactory.class)).thenReturn(lfuCacheFactory);
        when(lfuCacheFactory.createCacheManager(10)).thenReturn(mock(CacheManager.class));

        CacheManager<Long, NewsEntity> resultNewsCacheManager = cacheConfig.newsCacheManager();

        assertThat(resultNewsCacheManager).isNotNull();
        verify(applicationContext).getBean(LFUCacheFactory.class);
    }

    @Test
    void testCreateCommentCacheManagerLRU() {
        cacheConfig.setAlgorithm(CacheAlgorithm.LRU);
        when(applicationContext.getBean(LRUCacheFactory.class)).thenReturn(lruCacheFactory);
        when(lruCacheFactory.createCacheManager(10)).thenReturn(mock(CacheManager.class));

        CacheManager<Long, CommentEntity> resultCommentCacheManager = cacheConfig.commentCacheManager();

        assertThat(resultCommentCacheManager).isNotNull();
        verify(applicationContext).getBean(LRUCacheFactory.class);
    }
}