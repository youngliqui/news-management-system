package ru.clevertec.core.service.information.comment;

import org.springframework.data.domain.Page;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.core.entity.CommentEntity;

public interface CommentInformationService {
    Page<CommentInfoDTO> getComments(Long newsId, int size, int page);

    CommentEntity getCommentById(Long commentId);

    CommentInfoDTO getCommentInfoById(Long commentId, Long newsId);

    Page<CommentInfoDTO> searchComments(String query, int size, int page);
}
