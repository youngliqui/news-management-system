package ru.clevertec.clientapi.service.management.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.comment.CommentCreateDTO;
import ru.clevertec.clientapi.dto.comment.CommentInfoDTO;
import ru.clevertec.clientapi.dto.comment.CommentPatchDTO;
import ru.clevertec.clientapi.dto.comment.CommentUpdateDTO;
import ru.clevertec.clientapi.entity.CommentEntity;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.exception.CommentNotFoundException;
import ru.clevertec.clientapi.mapper.CommentMapper;
import ru.clevertec.clientapi.repository.CommentRepository;
import ru.clevertec.clientapi.service.information.news.NewsInformationService;

@Service
@RequiredArgsConstructor
public class CommentManagementServiceImpl implements CommentManagementService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsInformationService newsInformationService;


    @Override
    public CommentInfoDTO createComment(Long newsId, CommentCreateDTO commentCreateDTO) {
        NewsEntity news = newsInformationService.getNewsById(newsId);
        CommentEntity newComment = commentMapper.commentCreateDTOToComment(commentCreateDTO, news);

        return commentMapper.commentToCommentInfoDTO(commentRepository.save(newComment));
    }

    @Override
    public CommentInfoDTO updateComment(Long commentId, Long newsId, CommentUpdateDTO commentUpdateDTO) {
        CommentEntity comment = validateCommentAndGet(commentId, newsId);
        commentMapper.updateCommentFromDTO(comment, commentUpdateDTO);

        return commentMapper.commentToCommentInfoDTO(commentRepository.save(comment));
    }

    @Override
    public CommentInfoDTO patchComment(Long commentId, Long newsId, CommentPatchDTO commentPatchDTO) {
        CommentEntity comment = validateCommentAndGet(commentId, newsId);
        commentMapper.patchCommentFromDTO(comment, commentPatchDTO);

        return commentMapper.commentToCommentInfoDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(Long commentId, Long newsId) {
        CommentEntity comment = validateCommentAndGet(commentId, newsId);

        commentRepository.delete(comment);
    }

    private CommentEntity getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment with id=" + commentId + " was not found"));
    }

    private CommentEntity validateCommentAndGet(Long commentId, Long newsId) {
        NewsEntity news = newsInformationService.getNewsById(newsId);
        CommentEntity comment = getCommentById(commentId);

        if (!comment.getNews().getId().equals(news.getId())) {
            throw new CommentNotFoundException("Comment does not belong to the specified news");
        }

        return comment;
    }
}
