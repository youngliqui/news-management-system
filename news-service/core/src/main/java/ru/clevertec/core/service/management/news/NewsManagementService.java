package ru.clevertec.core.service.management.news;

import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;

public interface NewsManagementService {
    NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO);

    NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO);

    NewsInfoDTO patchNews(Long newsId, NewsPatchDTO newsPatchDTO);

    void deleteNewsById(Long newsId);
}
