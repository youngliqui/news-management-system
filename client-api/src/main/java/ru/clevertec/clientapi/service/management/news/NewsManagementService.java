package ru.clevertec.clientapi.service.management.news;

import ru.clevertec.clientapi.dto.NewsCreateDTO;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.dto.NewsUpdateDTO;

public interface NewsManagementService {
    NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO);

    NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO);

    NewsInfoDTO patchNews(Long newsId, NewsUpdateDTO newsUpdateDTO);

    void deleteNewsById(Long newsId);
}