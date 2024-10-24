package ru.clevertec.clientapi.service;

import org.springframework.data.domain.Page;
import ru.clevertec.clientapi.dto.NewsCreateDTO;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.dto.NewsUpdateDTO;
import ru.clevertec.clientapi.entity.NewsEntity;

public interface NewsService {

    Page<NewsInfoDTO> getAll(int size, int page);

    NewsEntity getNewsById(Long newsId);

    NewsInfoDTO getNewsInfoById(Long newsId);

    NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO);

    NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO);

    NewsInfoDTO patchNews(Long newsId, NewsUpdateDTO newsUpdateDTO);

    void deleteNewsById(Long newsId);
}
