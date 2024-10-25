package ru.clevertec.clientapi.service.information;

import org.springframework.data.domain.Page;
import ru.clevertec.clientapi.dto.CommentInfoDTO;

public interface CommentInformationService {
    Page<CommentInfoDTO> getComments(Long newsId, int size, int page);

    CommentInfoDTO getCommentInfoById(Long commentId);
}
