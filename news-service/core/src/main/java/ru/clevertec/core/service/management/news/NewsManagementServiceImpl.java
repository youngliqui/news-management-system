package ru.clevertec.core.service.management.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;
import ru.clevertec.common.exception.NewsNotFoundException;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.mapper.NewsMapper;
import ru.clevertec.core.repository.NewsRepository;

import java.util.Optional;

/**
 * Реализация сервиса управления новостями.
 * <p>
 * Этот класс реализует интерфейс {@link NewsManagementService} и предоставляет
 * методы для создания, обновления и удаления новостей,
 * включая кэширование результатов.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class NewsManagementServiceImpl implements NewsManagementService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CacheManager<Long, NewsEntity> cacheManager;

    @Override
    public NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO) {
        NewsEntity newNews = newsMapper.newsCreateDTOToNews(newsCreateDTO);
        NewsEntity savedNews = newsRepository.save(newNews);

        cacheManager.put(savedNews.getId(), savedNews);

        return newsMapper.newsToNewsInfoDTO(savedNews);
    }

    @Override
    public NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO) {
        NewsEntity news = getNewsById(newsId);
        newsMapper.updateNewsFromDTO(news, newsUpdateDTO);
        NewsEntity updatedNews = newsRepository.save(news);

        cacheManager.put(updatedNews.getId(), updatedNews);

        return newsMapper.newsToNewsInfoDTO(updatedNews);
    }

    @Override
    public NewsInfoDTO patchNews(Long newsId, NewsPatchDTO newsPatchDTO) {
        NewsEntity news = getNewsById(newsId);
        newsMapper.patchNewsFromDTO(news, newsPatchDTO);
        NewsEntity patchedNews = newsRepository.save(news);

        cacheManager.put(patchedNews.getId(), patchedNews);

        return newsMapper.newsToNewsInfoDTO(patchedNews);
    }

    @Override
    public void deleteNewsById(Long newsId) {
        NewsEntity newsEntity = getNewsById(newsId);

        newsRepository.delete(newsEntity);
        cacheManager.remove(newsId);
    }

    private NewsEntity getNewsById(Long newsId) {
        return Optional.ofNullable(cacheManager.get(newsId))
                .orElseGet(() -> {
                    NewsEntity news = newsRepository.findById(newsId)
                            .orElseThrow(() ->
                                    new NewsNotFoundException("News with id=" + newsId + " was not found"));
                    cacheManager.put(newsId, news);

                    return news;
                });
    }
}
