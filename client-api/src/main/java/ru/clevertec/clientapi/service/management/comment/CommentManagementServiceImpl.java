package ru.clevertec.clientapi.service.management.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.CommentCreateDTO;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.dto.CommentUpdateDTO;
import ru.clevertec.clientapi.entity.CommentEntity;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.exception.CommentNotFoundException;
import ru.clevertec.clientapi.mapper.CommentMapper;
import ru.clevertec.clientapi.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentManagementServiceImpl implements CommentManagementService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    @Override
    public CommentInfoDTO createComment(NewsEntity news, CommentCreateDTO commentCreateDTO) {
        CommentEntity newComment = commentMapper.commentCreateDTOToComment(commentCreateDTO, news);

        return commentMapper.commentToCommentInfoDTO(newComment);
    }

    @Override
    public CommentInfoDTO updateComment(Long commentId, CommentUpdateDTO commentUpdateDTO) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment with id=" + commentId + " was not found"));

        commentMapper.updateCommentFromDTO(comment, commentUpdateDTO);

        return commentMapper.commentToCommentInfoDTO(comment);
    }

    @Override
    public CommentInfoDTO patchComment(Long commentId, CommentUpdateDTO commentUpdateDTO) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment with id=" + commentId + " was not found"));

        commentMapper.patchCommentFromDTO(comment, commentUpdateDTO);

        return commentMapper.commentToCommentInfoDTO(comment);
    }

    @Override
    public void deleteCommentById(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment with id=" + commentId + " was not found"));

        commentRepository.delete(comment);
    }
}
