package ru.clevertec.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.feign_client.access.AccessControlService;
import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;
import ru.clevertec.core.service.information.comment.CommentInformationService;
import ru.clevertec.core.service.management.comment.CommentManagementService;

import java.util.List;

/**
 * Контроллер для управления комментариями.
 * <p>
 * Этот контроллер предоставляет REST API для выполнения операций
 * над комментариями, связанных с новостями, включая создание,
 * обновление, удаление и получение комментариев.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final CommentInformationService commentInformationService;
    private final CommentManagementService commentManagementService;
    private final AccessControlService accessControlService;

    /**
     * Получить все комментарии к новости.
     *
     * @param newsId идентификатор новости
     * @param size   количество комментариев на странице
     * @param page   номер страницы
     * @return страница комментариев
     */
    @GetMapping("/news/{newsId}/comments")
    public Page<CommentInfoDTO> getNewsComments(
            @PathVariable Long newsId,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return commentInformationService.getComments(newsId, size, page);
    }

    /**
     * Получить комментарий по его идентификатору.
     *
     * @param newsId    идентификатор новости
     * @param commentId идентификатор комментария
     * @return информация о комментарии
     */
    @GetMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO getCommentById(
            @PathVariable Long newsId,
            @PathVariable Long commentId
    ) {
        return commentInformationService.getCommentInfoById(commentId, newsId);
    }

    /**
     * Выполнить полнотекстовый поиск комментариев.
     *
     * @param query строка запроса для поиска
     * @param size  количество комментариев на странице
     * @param page  номер страницы
     * @return страница найденных комментариев
     */
    @GetMapping("/comments/search")
    public Page<CommentInfoDTO> fullTextSearch(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return commentInformationService.searchComments(query, size, page);
    }

    /**
     * Создать новый комментарий к новости.
     *
     * @param bearerToken      токен авторизации пользователя
     * @param newsId           идентификатор новости
     * @param commentCreateDTO данные для создания комментария
     * @return созданный комментарий
     */
    @PostMapping("/news/{newsId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentInfoDTO createComment(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @Valid @RequestBody CommentCreateDTO commentCreateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        return commentManagementService.createComment(newsId, commentCreateDTO);
    }

    /**
     * Обновить существующий комментарий.
     *
     * @param bearerToken      токен авторизации пользователя
     * @param newsId           идентификатор новости
     * @param commentId        идентификатор комментария
     * @param commentUpdateDTO данные для обновления комментария
     * @return обновленный комментарий
     */
    @PutMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO updateComment(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateDTO commentUpdateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        return commentManagementService.updateComment(commentId, newsId, commentUpdateDTO);
    }

    /**
     * Частично обновить существующий комментарий.
     *
     * @param bearerToken     токен авторизации пользователя
     * @param newsId          идентификатор новости
     * @param commentId       идентификатор комментария
     * @param commentPatchDTO данные для частичного обновления комментария
     * @return обновленный комментарий после частичного изменения
     */
    @PatchMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO patchComment(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentPatchDTO commentPatchDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        return commentManagementService.patchComment(commentId, newsId, commentPatchDTO);
    }

    /**
     * Удалить существующий комментарий.
     *
     * @param bearerToken токен авторизации пользователя
     * @param newsId      идентификатор новости
     * @param commentId   идентификатор комментария
     */
    @DeleteMapping("/news/{newsId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @PathVariable Long commentId
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        commentManagementService.deleteCommentById(commentId, newsId);
    }
}
