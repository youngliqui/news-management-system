package ru.clevertec.clientapi.service.information.news;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsInformationServiceImpl implements NewsInformationService {
    private final NewsRepository newsRepository;


    @Override
    public Page<NewsInfoDTO> getAll(int size, int page) {
        return null;
    }

    @Override
    public NewsEntity getNewsById(Long newsId) {
        return null;
    }

    @Override
    public NewsInfoDTO getNewsInfoById(Long newsId) {
        return null;
    }

    @Override
    public Page<NewsInfoDTO> fullTextSearch(String text, int size, int page) {
        return null;
    }
}
