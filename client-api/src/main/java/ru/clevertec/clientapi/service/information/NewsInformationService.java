package ru.clevertec.clientapi.service.information;

import org.springframework.data.domain.Page;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.entity.NewsEntity;

public interface NewsInformationService {
    Page<NewsInfoDTO> getAll(int size, int page);

    NewsEntity getNewsById(Long newsId);

    NewsInfoDTO getNewsInfoById(Long newsId);

    Page<NewsInfoDTO> fullTextSearch(String text, int size, int page);
}
