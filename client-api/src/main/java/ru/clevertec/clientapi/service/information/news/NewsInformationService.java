package ru.clevertec.clientapi.service.information.news;

import org.springframework.data.domain.Page;
import ru.clevertec.clientapi.dto.news.NewsInfoDTO;
import ru.clevertec.clientapi.entity.NewsEntity;

public interface NewsInformationService {
    Page<NewsInfoDTO> getAll(int size, int page);

    NewsEntity getNewsById(Long newsId);

    NewsInfoDTO getNewsInfoById(Long newsId);

    Page<NewsInfoDTO> searchNews(String title, String text, int size, int page);
}
