package ru.clevertec.clientapi.service.management.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.NewsCreateDTO;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.dto.NewsUpdateDTO;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.exception.NewsNotFoundException;
import ru.clevertec.clientapi.mapper.NewsMapper;
import ru.clevertec.clientapi.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsManagementServiceImpl implements NewsManagementService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO) {
        NewsEntity newNews = newsMapper.newsCreateDTOToNews(newsCreateDTO);

        return newsMapper.newsToNewsInfoDTO(newsRepository.save(newNews));
    }

    @Override
    public NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO) {
        NewsEntity newsEntity = newsRepository.findById(newsId)
                .orElseThrow(() ->
                        new NewsNotFoundException("News with id=" + newsId + " was not found"));

        NewsEntity updatedNews = newsMapper.updateNews(newsEntity, newsUpdateDTO);

        return newsMapper.newsToNewsInfoDTO(updatedNews);
    }

    @Override
    public NewsInfoDTO patchNews(Long newsId, NewsUpdateDTO newsUpdateDTO) {
        NewsEntity newsEntity = newsRepository.findById(newsId)
                .orElseThrow(() ->
                        new NewsNotFoundException("News with id=" + newsId + " was not found"));

        NewsEntity patchedNews = newsMapper.patchNews(newsEntity, newsUpdateDTO);

        return newsMapper.newsToNewsInfoDTO(patchedNews);
    }

    @Override
    public void deleteNewsById(Long newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId)
                .orElseThrow(() ->
                        new NewsNotFoundException("News with id=" + newsId + " was not found"));

        newsRepository.delete(newsEntity);
    }
}
