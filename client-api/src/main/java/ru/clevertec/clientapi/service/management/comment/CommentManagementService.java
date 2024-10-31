package ru.clevertec.clientapi.service.management.comment;

import ru.clevertec.clientapi.dto.CommentCreateDTO;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.dto.CommentUpdateDTO;

public interface CommentManagementService {
    CommentInfoDTO createComment(Long newsId, CommentCreateDTO commentCreateDTO);

    CommentInfoDTO updateComment(Long commentId, Long newsId, CommentUpdateDTO commentUpdateDTO);

    CommentInfoDTO patchComment(Long commentId, Long newsId, CommentUpdateDTO commentUpdateDTO);

    void deleteCommentById(Long commentId, Long newsId);
}
