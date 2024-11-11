package ru.clevertec.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.feign_client.access.AccessControlService;
import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;
import ru.clevertec.core.service.information.news.NewsInformationService;
import ru.clevertec.core.service.management.news.NewsManagementService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsInformationService newsInformationService;
    private final NewsManagementService newsManagementService;
    private final AccessControlService accessControlService;

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
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return newsInformationService.searchNews(query, size, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsInfoDTO createNews(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody NewsCreateDTO newsCreateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

        return newsManagementService.createNews(newsCreateDTO);
    }

    @PutMapping("/{newsId}")
    public NewsInfoDTO updateNews(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @Valid @RequestBody NewsUpdateDTO newsUpdateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

        return newsManagementService.updateNews(newsId, newsUpdateDTO);
    }

    @PatchMapping("/{newsId}")
    public NewsInfoDTO patchNews(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @Valid @RequestBody NewsPatchDTO newsPatchDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

        return newsManagementService.patchNews(newsId, newsPatchDTO);
    }

    @DeleteMapping("/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

        newsManagementService.deleteNewsById(newsId);
    }
}
