package ru.clevertec.core.service.management.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;
import ru.clevertec.common.exception.CommentNotFoundException;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.mapper.CommentMapper;
import ru.clevertec.core.repository.CommentRepository;
import ru.clevertec.core.service.information.news.NewsInformationService;

import java.util.Optional;

/**
 * Реализация сервиса управления комментариями.
 * <p>
 * Этот класс реализует интерфейс {@link CommentManagementService} и предоставляет
 * методы для создания, обновления и удаления комментариев,
 * включая кэширование результатов.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CommentManagementServiceImpl implements CommentManagementService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsInformationService newsInformationService;
    private final CacheManager<Long, CommentEntity> cacheManager;


    @Override
    public CommentInfoDTO createComment(Long newsId, CommentCreateDTO commentCreateDTO) {
        NewsEntity news = newsInformationService.getNewsById(newsId);
        CommentEntity newComment = commentMapper.commentCreateDTOToComment(commentCreateDTO, news);
        CommentEntity createdComment = commentRepository.save(newComment);

        cacheManager.put(createdComment.getId(), createdComment);

        return commentMapper.commentToCommentInfoDTO(createdComment);
    }

    @Override
    public CommentInfoDTO updateComment(Long commentId, Long newsId, CommentUpdateDTO commentUpdateDTO) {
        CommentEntity comment = validateCommentAndGet(commentId, newsId);
        commentMapper.updateCommentFromDTO(comment, commentUpdateDTO);
        CommentEntity updatedComment = commentRepository.save(comment);

        cacheManager.put(updatedComment.getId(), updatedComment);

        return commentMapper.commentToCommentInfoDTO(updatedComment);
    }

    @Override
    public CommentInfoDTO patchComment(Long commentId, Long newsId, CommentPatchDTO commentPatchDTO) {
        CommentEntity comment = validateCommentAndGet(commentId, newsId);
        commentMapper.patchCommentFromDTO(comment, commentPatchDTO);
        CommentEntity patchedComment = commentRepository.save(comment);

        cacheManager.put(patchedComment.getId(), patchedComment);

        return commentMapper.commentToCommentInfoDTO(patchedComment);
    }

    @Override
    public void deleteCommentById(Long commentId, Long newsId) {
        CommentEntity comment = validateCommentAndGet(commentId, newsId);

        commentRepository.delete(comment);
        cacheManager.remove(commentId);
    }

    private CommentEntity getCommentById(Long commentId) {
        return Optional.ofNullable(cacheManager.get(commentId))
                .orElseGet(() -> {
                    CommentEntity comment = commentRepository.findById(commentId)
                            .orElseThrow(() ->
                                    new CommentNotFoundException("Comment with id=" + commentId + " was not found"));
                    cacheManager.put(commentId, comment);

                    return comment;
                });
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
