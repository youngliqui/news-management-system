package ru.clevertec.clientapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.clientapi.dto.news.NewsCreateDTO;
import ru.clevertec.clientapi.dto.news.NewsInfoDTO;
import ru.clevertec.clientapi.dto.news.NewsPatchDTO;
import ru.clevertec.clientapi.dto.news.NewsUpdateDTO;
import ru.clevertec.clientapi.service.information.news.NewsInformationService;
import ru.clevertec.clientapi.service.management.news.NewsManagementService;

@RestController
@RequestMapping("/api/news")
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
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String text,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return newsInformationService.searchNews(title, text, size, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsInfoDTO createNews(
            @Valid @RequestBody NewsCreateDTO newsCreateDTO
    ) {
        return newsManagementService.createNews(newsCreateDTO);
    }

    @PutMapping("/{newsId}")
    public NewsInfoDTO updateNews(
            @PathVariable Long newsId,
            @Valid @RequestBody NewsUpdateDTO newsUpdateDTO
    ) {
        return newsManagementService.updateNews(newsId, newsUpdateDTO);
    }

    @PatchMapping("/{newsId}")
    public NewsInfoDTO patchNews(
            @PathVariable Long newsId,
            @Valid @RequestBody NewsPatchDTO newsPatchDTO
    ) {
        return newsManagementService.patchNews(newsId, newsPatchDTO);
    }

    @DeleteMapping("/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(
            @PathVariable Long newsId
    ) {
        newsManagementService.deleteNewsById(newsId);
    }
}
