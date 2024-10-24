package ru.clevertec.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.clientapi.dto.NewsCreateDTO;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.dto.NewsUpdateDTO;
import ru.clevertec.clientapi.service.NewsService;

@RestController("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public Page<NewsInfoDTO> getAllNews(
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return newsService.getAll(size, page);
    }

    @GetMapping("/{newsId}")
    public NewsInfoDTO getNewsById(
            @PathVariable Long newsId
    ) {
        return newsService.getNewsInfoById(newsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsInfoDTO createNews(
            @RequestBody NewsCreateDTO newsCreateDTO
    ) {
        return newsService.createNews(newsCreateDTO);
    }

    @PutMapping("/{newsId}")
    public NewsInfoDTO updateNews(
            @PathVariable Long newsId,
            @RequestBody NewsUpdateDTO newsUpdateDTO
    ) {
        return newsService.updateNews(newsId, newsUpdateDTO);
    }

    @PatchMapping("/{newsId}")
    public NewsInfoDTO patchNews(
            @PathVariable Long newsId,
            @RequestBody NewsUpdateDTO newsUpdateDTO
    ) {
        return newsService.patchNews(newsId, newsUpdateDTO);
    }

    @DeleteMapping("/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(
            @PathVariable Long newsId
    ) {
        newsService.deleteNewsById(newsId);
    }
}
