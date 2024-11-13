package ru.clevertec.core.service.information.news;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.exception.NewsNotFoundException;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.mapper.NewsMapper;
import ru.clevertec.core.repository.NewsRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsInformationServiceImplTest {

    @InjectMocks
    private NewsInformationServiceImpl newsInformationService;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private CacheManager<Long, NewsEntity> cacheManager;

    private NewsEntity newsEntity;

    private NewsInfoDTO newsInfoDTO;

    @BeforeEach
    void setUp() {
        String testText = "test";
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 1, 1);

        newsEntity = NewsEntity.builder()
                .id(1L)
                .text(testText)
                .title(testText)
                .time(time)
                .build();

        newsInfoDTO = NewsInfoDTO.builder()
                .id(newsEntity.getId())
                .text(newsEntity.getText())
                .title(newsEntity.getTitle())
                .time(newsEntity.getTime().toString())
                .build();
    }

    @Test
    void testGetAll() {
        int size = 10;
        int page = 0;

        when(newsRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(newsEntity)));
        when(newsMapper.newsToNewsInfoDTO(any(NewsEntity.class))).thenReturn(newsInfoDTO);

        Page<NewsInfoDTO> result = newsInformationService.getAll(size, page);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(newsRepository).findAll(PageRequest.of(page, size));
    }

    @Test
    void testGetNewsByIdCached() {
        Long newsId = 1L;

        when(cacheManager.get(newsId)).thenReturn(newsEntity);

        NewsEntity result = newsInformationService.getNewsById(newsId);

        assertThat(result).isEqualTo(newsEntity);
        verify(cacheManager).get(newsId);
        verify(newsRepository, never()).findById(anyLong());
    }

    @Test
    void testGetNewsByIdNotCached() {
        Long newsId = 1L;

        when(cacheManager.get(newsId)).thenReturn(null);
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsEntity));

        NewsEntity result = newsInformationService.getNewsById(newsId);

        assertThat(result).isEqualTo(newsEntity);
        verify(cacheManager).put(newsId, newsEntity);
    }

    @Test
    void shouldThrowNotFoundException() {
        Long newsId = 1L;

        when(cacheManager.get(newsId)).thenReturn(null);
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        assertThrows(NewsNotFoundException.class, () ->
                newsInformationService.getNewsById(newsId));
    }

    @Test
    void testGetNewsInfoById() {
        Long newsId = 1L;

        when(cacheManager.get(newsId)).thenReturn(newsEntity);
        when(newsMapper.newsToNewsInfoDTO(any(NewsEntity.class))).thenReturn(newsInfoDTO);

        NewsInfoDTO result = newsInformationService.getNewsInfoById(newsId);

        assertThat(result).isEqualTo(newsInfoDTO);
    }

    @Test
    void testSearchNews() {
        String query = "test";
        int size = 10;
        int page = 0;

        when(newsRepository.searchByTitleOrText(eq(query), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(newsEntity)));
        when(newsMapper.newsToNewsInfoDTO(any(NewsEntity.class))).thenReturn(newsInfoDTO);

        Page<NewsInfoDTO> result = newsInformationService.searchNews(query, 10, 0);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(newsRepository).searchByTitleOrText(query, PageRequest.of(page, size));
    }
}