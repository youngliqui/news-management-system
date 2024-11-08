package ru.clevertec.core.service.information.news;

import org.springframework.data.domain.Page;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.core.entity.NewsEntity;

public interface NewsInformationService {
    Page<NewsInfoDTO> getAll(int size, int page);

    NewsEntity getNewsById(Long newsId);

    NewsInfoDTO getNewsInfoById(Long newsId);

    Page<NewsInfoDTO> searchNews(String query, int size, int page);
}
