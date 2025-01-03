package ru.clevertec.core.service.information.news;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.exception.NewsNotFoundException;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.mapper.NewsMapper;
import ru.clevertec.core.repository.NewsRepository;

import java.util.Optional;

/**
 * Реализация сервиса получения информации о новостях.
 * <p>
 * Этот класс реализует интерфейс {@link NewsInformationService} и предоставляет
 * методы для получения и обработки информации о новостях,
 * включая кэширование результатов.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class NewsInformationServiceImpl implements NewsInformationService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CacheManager<Long, NewsEntity> cacheManager;


    @Override
    public Page<NewsInfoDTO> getAll(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return newsRepository.findAll(pageable)
                .map(newsMapper::newsToNewsInfoDTO);
    }

    @Override
    public NewsEntity getNewsById(Long newsId) {
        return Optional.ofNullable(cacheManager.get(newsId))
                .orElseGet(() -> {
                    NewsEntity news = newsRepository.findById(newsId)
                            .orElseThrow(() ->
                                    new NewsNotFoundException("News with id=" + newsId + " was not found"));
                    cacheManager.put(newsId, news);

                    return news;
                });
    }

    @Override
    public NewsInfoDTO getNewsInfoById(Long newsId) {
        return newsMapper.newsToNewsInfoDTO(getNewsById(newsId));
    }

    @Override
    public Page<NewsInfoDTO> searchNews(String query, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return newsRepository.searchByTitleOrText(query, pageable)
                .map(newsMapper::newsToNewsInfoDTO);
    }
}
