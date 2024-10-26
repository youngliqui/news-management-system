package ru.clevertec.clientapi.service.information.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentInformationServiceImpl implements CommentInformationService {
    private final CommentRepository commentRepository;

    @Override
    public Page<CommentInfoDTO> getComments(Long newsId, int size, int page) {
        return null;
    }

    @Override
    public CommentInfoDTO getCommentInfoById(Long commentId) {
        return null;
    }
}
