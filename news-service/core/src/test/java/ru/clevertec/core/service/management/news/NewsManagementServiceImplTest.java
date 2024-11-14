package ru.clevertec.core.service.management.news;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;
import ru.clevertec.common.exception.NewsNotFoundException;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.mapper.NewsMapper;
import ru.clevertec.core.repository.NewsRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsManagementServiceImplTest {

    @InjectMocks
    private NewsManagementServiceImpl newsManagementService;

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
                .time(newsEntity.getTime().toString())
                .title(newsEntity.getTitle())
                .text(newsEntity.getText())
                .commentsCount(0)
                .build();
    }

    @Test
    void testCreateNews() {
        NewsCreateDTO newsCreateDTO = new NewsCreateDTO();

        when(newsMapper.newsCreateDTOToNews(newsCreateDTO)).thenReturn(newsEntity);
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);
        when(newsMapper.newsToNewsInfoDTO(newsEntity)).thenReturn(newsInfoDTO);

        NewsInfoDTO result = newsManagementService.createNews(newsCreateDTO);

        assertThat(result).isEqualTo(newsInfoDTO);
        verify(newsMapper).newsCreateDTOToNews(newsCreateDTO);
        verify(newsRepository).save(newsEntity);
        verify(cacheManager).put(newsEntity.getId(), newsEntity);
    }

    @Test
    void testUpdateNews() {
        Long newsId = 1L;
        NewsUpdateDTO newsUpdateDTO = new NewsUpdateDTO();

        when(cacheManager.get(newsId)).thenReturn(newsEntity);
        when(newsRepository.save(any(NewsEntity.class))).thenReturn(newsEntity);
        when(newsMapper.newsToNewsInfoDTO(newsEntity)).thenReturn(newsInfoDTO);

        NewsInfoDTO result = newsManagementService.updateNews(newsId, newsUpdateDTO);

        assertThat(result).isEqualTo(newsInfoDTO);
        verify(newsMapper).updateNewsFromDTO(newsEntity, newsUpdateDTO);
        verify(newsRepository).save(newsEntity);
        verify(cacheManager).put(newsId, newsEntity);
    }

    @Test
    void testPatchNews() {
        Long newsId = 1L;
        NewsPatchDTO newsPatchDTO = new NewsPatchDTO();

        when(cacheManager.get(newsId)).thenReturn(newsEntity);
        when(newsRepository.save(any(NewsEntity.class))).thenReturn(newsEntity);
        when(newsMapper.newsToNewsInfoDTO(newsEntity)).thenReturn(newsInfoDTO);

        NewsInfoDTO result = newsManagementService.patchNews(newsId, newsPatchDTO);

        assertThat(result).isEqualTo(newsInfoDTO);
        verify(newsMapper).patchNewsFromDTO(newsEntity, newsPatchDTO);
        verify(newsRepository).save(newsEntity);
        verify(cacheManager).put(newsId, newsEntity);
    }

    @Test
    void testDeleteNews() {
        Long newsId = 1L;

        when(cacheManager.get(newsId)).thenReturn(newsEntity);

        newsManagementService.deleteNewsById(newsId);

        verify(cacheManager).get(newsId);
        verify(newsRepository).delete(newsEntity);
        verify(cacheManager).remove(newsId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdateNewsWithIncorrectId() {
        Long incorrectNewsId = 100L;

        when(cacheManager.get(incorrectNewsId)).thenReturn(null);
        when(newsRepository.findById(incorrectNewsId)).thenReturn(Optional.empty());

        assertThrows(NewsNotFoundException.class, () ->
                newsManagementService.updateNews(incorrectNewsId, new NewsUpdateDTO()));
    }

    @Test
    void testGetNewsById() {
        Long newsId = 1L;

        when(cacheManager.get(newsId)).thenReturn(null);
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsEntity));

        assertDoesNotThrow(() ->
                newsManagementService.deleteNewsById(newsId));

        verify(cacheManager).get(newsId);
        verify(newsRepository).findById(newsId);
    }
}