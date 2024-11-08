package ru.clevertec.core.service.management.comment;

import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;

public interface CommentManagementService {
    CommentInfoDTO createComment(Long newsId, CommentCreateDTO commentCreateDTO);

    CommentInfoDTO updateComment(Long commentId, Long newsId, CommentUpdateDTO commentUpdateDTO);

    CommentInfoDTO patchComment(Long commentId, Long newsId, CommentPatchDTO commentPatchDTO);

    void deleteCommentById(Long commentId, Long newsId);
}
