package ru.clevertec.clientapi.service.information.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.exception.CommentNotFoundException;
import ru.clevertec.clientapi.mapper.CommentMapper;
import ru.clevertec.clientapi.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentInformationServiceImpl implements CommentInformationService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Page<CommentInfoDTO> getComments(Long newsId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return commentRepository.findAll(pageable)
                .map(commentMapper::commentToCommentInfoDTO);
    }

    @Override
    public CommentInfoDTO getCommentInfoById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::commentToCommentInfoDTO)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment with id=" + commentId + " was not found"));
    }
}
