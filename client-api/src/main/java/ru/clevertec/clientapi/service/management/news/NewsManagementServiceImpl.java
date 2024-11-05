package ru.clevertec.clientapi.service.management.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.cache.CacheManager;
import ru.clevertec.clientapi.dto.news.NewsCreateDTO;
import ru.clevertec.clientapi.dto.news.NewsInfoDTO;
import ru.clevertec.clientapi.dto.news.NewsPatchDTO;
import ru.clevertec.clientapi.dto.news.NewsUpdateDTO;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.exception.NewsNotFoundException;
import ru.clevertec.clientapi.mapper.NewsMapper;
import ru.clevertec.clientapi.repository.NewsRepository;

import java.util.Optional;

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
