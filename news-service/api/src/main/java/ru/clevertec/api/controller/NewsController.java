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

/**
 * Контроллер для управления новостями.
 * <p>
 * Этот контроллер предоставляет REST API для выполнения операций
 * над новостями, включая создание, обновление, удаление и получение новостей.
 * </p>
 */
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsInformationService newsInformationService;
    private final NewsManagementService newsManagementService;
    private final AccessControlService accessControlService;

    /**
     * Получить все новости с пагинацией.
     *
     * @param size количество новостей на странице
     * @param page номер страницы
     * @return страница новостей
     */
    @GetMapping
    public Page<NewsInfoDTO> getAllNews(
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return newsInformationService.getAll(size, page);
    }

    /**
     * Получить новость по её идентификатору.
     *
     * @param newsId идентификатор новости
     * @return информация о новости
     */
    @GetMapping("/{newsId}")
    public NewsInfoDTO getNewsById(
            @PathVariable Long newsId
    ) {
        return newsInformationService.getNewsInfoById(newsId);
    }

    /**
     * Выполнить поиск новостей по запросу.
     *
     * @param query строка запроса для поиска
     * @param size  количество новостей на странице
     * @param page  номер страницы
     * @return страница найденных новостей
     */
    @GetMapping("/search")
    public Page<NewsInfoDTO> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return newsInformationService.searchNews(query, size, page);
    }

    /**
     * Создать новую новость.
     *
     * @param bearerToken   токен авторизации пользователя
     * @param newsCreateDTO данные для создания новости
     * @return созданная новость
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsInfoDTO createNews(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody NewsCreateDTO newsCreateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

        return newsManagementService.createNews(newsCreateDTO);
    }

    /**
     * Обновить существующую новость.
     *
     * @param bearerToken   токен авторизации пользователя
     * @param newsId        идентификатор новости
     * @param newsUpdateDTO данные для обновления новости
     * @return обновленная новость
     */
    @PutMapping("/{newsId}")
    public NewsInfoDTO updateNews(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @Valid @RequestBody NewsUpdateDTO newsUpdateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

        return newsManagementService.updateNews(newsId, newsUpdateDTO);
    }

    /**
     * Частично обновить существующую новость.
     *
     * @param bearerToken  токен авторизации пользователя
     * @param newsId       идентификатор новости
     * @param newsPatchDTO данные для частичного обновления новости
     * @return обновленная новость после частичного изменения
     */
    @PatchMapping("/{newsId}")
    public NewsInfoDTO patchNews(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @Valid @RequestBody NewsPatchDTO newsPatchDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

        return newsManagementService.patchNews(newsId, newsPatchDTO);
    }

    /**
     * Удалить существующую новость.
     *
     * @param bearerToken токен авторизации пользователя
     * @param newsId      идентификатор новости
     */
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
