package ru.clevertec.clientapi.service.management.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.NewsCreateDTO;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.dto.NewsUpdateDTO;
import ru.clevertec.clientapi.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsManagementServiceImpl implements NewsManagementService {
    private final NewsRepository newsRepository;

    @Override
    public NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO) {
        return null;
    }

    @Override
    public NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO) {
        return null;
    }

    @Override
    public NewsInfoDTO patchNews(Long newsId, NewsUpdateDTO newsUpdateDTO) {
        return null;
    }

    @Override
    public void deleteNewsById(Long newsId) {

    }
}
