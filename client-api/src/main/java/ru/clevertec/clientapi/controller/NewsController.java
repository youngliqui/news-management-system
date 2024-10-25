package ru.clevertec.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.clientapi.dto.NewsCreateDTO;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.dto.NewsUpdateDTO;
import ru.clevertec.clientapi.service.information.NewsInformationService;
import ru.clevertec.clientapi.service.management.NewsManagementService;

@RestController("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsInformationService newsInformationService;
    private final NewsManagementService newsManagementService;

    @GetMapping
    public Page<NewsInfoDTO> getAllNews(
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return newsInformationService.getAll(size, page);
    }

    @GetMapping("/{newsId}")
    public NewsInfoDTO getNewsById(
            @PathVariable Long newsId
    ) {
        return newsInformationService.getNewsInfoById(newsId);
    }

    @GetMapping("/search")
    public Page<NewsInfoDTO> search(
            @RequestParam String text,
            @RequestParam(required = false) int size,
            @RequestParam(required = false) int page
    ) {
        return newsInformationService.fullTextSearch(text, size, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsInfoDTO createNews(
            @RequestBody NewsCreateDTO newsCreateDTO
    ) {
        return newsManagementService.createNews(newsCreateDTO);
    }

    @PutMapping("/{newsId}")
    public NewsInfoDTO updateNews(
            @PathVariable Long newsId,
            @RequestBody NewsUpdateDTO newsUpdateDTO
    ) {
        return newsManagementService.updateNews(newsId, newsUpdateDTO);
    }

    @PatchMapping("/{newsId}")
    public NewsInfoDTO patchNews(
            @PathVariable Long newsId,
            @RequestBody NewsUpdateDTO newsUpdateDTO
    ) {
        return newsManagementService.patchNews(newsId, newsUpdateDTO);
    }

    @DeleteMapping("/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(
            @PathVariable Long newsId
    ) {
        newsManagementService.deleteNewsById(newsId);
    }
}
