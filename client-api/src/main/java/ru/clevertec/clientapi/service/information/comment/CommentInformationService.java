package ru.clevertec.clientapi.service.information.comment;

import org.springframework.data.domain.Page;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.entity.CommentEntity;

public interface CommentInformationService {
    Page<CommentInfoDTO> getComments(Long newsId, int size, int page);

    CommentEntity getCommentById(Long commentId);

    CommentInfoDTO getCommentInfoById(Long commentId, Long newsId);

    Page<CommentInfoDTO> fullTextSearch(String text, int size, int page);
}
