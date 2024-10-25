package ru.clevertec.clientapi.service.management;

import ru.clevertec.clientapi.dto.CommentCreateDTO;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.dto.CommentUpdateDTO;
import ru.clevertec.clientapi.entity.NewsEntity;

public interface CommentManagementService {
    CommentInfoDTO createComment(NewsEntity news, CommentCreateDTO commentCreateDTO);

    CommentInfoDTO updateComment(Long commentId, CommentUpdateDTO commentUpdateDTO);

    CommentInfoDTO patchComment(Long commentId, CommentUpdateDTO commentUpdateDTO);

    void deleteCommentById(Long commentId);
}
