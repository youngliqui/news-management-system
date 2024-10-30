package ru.clevertec.clientapi.service.information.news;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.exception.NewsNotFoundException;
import ru.clevertec.clientapi.mapper.NewsMapper;
import ru.clevertec.clientapi.repository.NewsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsInformationServiceImpl implements NewsInformationService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;


    @Override
    public Page<NewsInfoDTO> getAll(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return newsRepository.findAll(pageable)
                .map(newsMapper::newsToNewsInfoDTO);
    }

    @Override
    public NewsEntity getNewsById(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() ->
                        new NewsNotFoundException("News with id=" + newsId + " was not found"));
    }

    @Override
    public NewsInfoDTO getNewsInfoById(Long newsId) {
        return newsMapper.newsToNewsInfoDTO(getNewsById(newsId));
    }

    @Override
    public Page<NewsInfoDTO> fullTextSearch(String text, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<NewsEntity> news = newsRepository.searchByText(text);

        return new PageImpl<>(
                news.stream()
                        .map(newsMapper::newsToNewsInfoDTO)
                        .collect(Collectors.toList()),
                pageable,
                news.size()
        );
    }
}
