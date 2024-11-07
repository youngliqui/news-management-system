package ru.clevertec.clientapi.service.information.news;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.cache.CacheManager;
import ru.clevertec.clientapi.dto.news.NewsInfoDTO;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.exception.NewsNotFoundException;
import ru.clevertec.clientapi.mapper.NewsMapper;
import ru.clevertec.clientapi.repository.NewsRepository;

import java.util.Optional;

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
