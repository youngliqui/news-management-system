package ru.clevertec.core.service.management.comment;

import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;

/**
 * Интерфейс для управления комментариями.
 * <p>
 * Этот интерфейс определяет методы для создания, обновления,
 * частичного обновления и удаления комментариев.
 * </p>
 */
public interface CommentManagementService {
    /**
     * Создает новый комментарий к указанной новости.
     *
     * @param newsId           идентификатор новости, к которой относится комментарий
     * @param commentCreateDTO данные для создания нового комментария
     * @return DTO созданного комментария
     */
    CommentInfoDTO createComment(Long newsId, CommentCreateDTO commentCreateDTO);

    /**
     * Обновляет существующий комментарий.
     *
     * @param commentId        идентификатор комментария, который нужно обновить
     * @param newsId           идентификатор новости, к которой относится комментарий
     * @param commentUpdateDTO данные для обновления комментария
     * @return DTO обновленного комментария
     */
    CommentInfoDTO updateComment(Long commentId, Long newsId, CommentUpdateDTO commentUpdateDTO);

    /**
     * Частично обновляет существующий комментарий.
     *
     * @param commentId       идентификатор комментария, который нужно частично обновить
     * @param newsId          идентификатор новости, к которой относится комментарий
     * @param commentPatchDTO данные для частичного обновления комментария
     * @return DTO обновленного комментария после частичного изменения
     */
    CommentInfoDTO patchComment(Long commentId, Long newsId, CommentPatchDTO commentPatchDTO);

    /**
     * Удаляет комментарий по его идентификатору.
     *
     * @param commentId идентификатор комментария, который нужно удалить
     * @param newsId    идентификатор новости, к которой относится комментарий
     */
    void deleteCommentById(Long commentId, Long newsId);
}
