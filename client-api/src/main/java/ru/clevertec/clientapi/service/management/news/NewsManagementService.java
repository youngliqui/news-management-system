package ru.clevertec.clientapi.service.management.news;

import ru.clevertec.clientapi.dto.news.NewsCreateDTO;
import ru.clevertec.clientapi.dto.news.NewsInfoDTO;
import ru.clevertec.clientapi.dto.news.NewsPatchDTO;
import ru.clevertec.clientapi.dto.news.NewsUpdateDTO;

public interface NewsManagementService {
    NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO);

    NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO);

    NewsInfoDTO patchNews(Long newsId, NewsPatchDTO newsPatchDTO);

    void deleteNewsById(Long newsId);
}
