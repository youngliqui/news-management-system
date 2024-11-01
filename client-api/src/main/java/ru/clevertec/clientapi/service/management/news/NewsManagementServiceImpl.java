package ru.clevertec.clientapi.service.management.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.news.NewsCreateDTO;
import ru.clevertec.clientapi.dto.news.NewsInfoDTO;
import ru.clevertec.clientapi.dto.news.NewsPatchDTO;
import ru.clevertec.clientapi.dto.news.NewsUpdateDTO;
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
        NewsEntity newsEntity = getNewsById(newsId);
        newsMapper.updateNewsFromDTO(newsEntity, newsUpdateDTO);

        return newsMapper.newsToNewsInfoDTO(newsRepository.save(newsEntity));
    }

    @Override
    public NewsInfoDTO patchNews(Long newsId, NewsPatchDTO newsPatchDTO) {
        NewsEntity newsEntity = getNewsById(newsId);
        newsMapper.patchNewsFromDTO(newsEntity, newsPatchDTO);

        return newsMapper.newsToNewsInfoDTO(newsRepository.save(newsEntity));
    }

    @Override
    public void deleteNewsById(Long newsId) {
        NewsEntity newsEntity = getNewsById(newsId);

        newsRepository.delete(newsEntity);
    }

    private NewsEntity getNewsById(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() ->
                        new NewsNotFoundException("News with id=" + newsId + " was not found"));
    }
}
