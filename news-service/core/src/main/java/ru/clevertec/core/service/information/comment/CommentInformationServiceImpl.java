package ru.clevertec.core.service.information.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.exception.CommentNotFoundException;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.mapper.CommentMapper;
import ru.clevertec.core.repository.CommentRepository;
import ru.clevertec.core.service.information.news.NewsInformationService;

import java.util.Optional;

/**
 * Реализация сервиса получения информации о комментариях.
 * <p>
 * Этот класс реализует интерфейс {@link CommentInformationService} и предоставляет
 * методы для получения и обработки информации о комментариях,
 * включая кэширование результатов.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CommentInformationServiceImpl implements CommentInformationService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsInformationService newsInformationService;
    private final CacheManager<Long, CommentEntity> cacheManager;

    @Override
    public Page<CommentInfoDTO> getComments(Long newsId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return commentRepository.findAllByNews_Id(newsId, pageable)
                .map(commentMapper::commentToCommentInfoDTO);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {
        return Optional.ofNullable(cacheManager.get(commentId))
                .orElseGet(() -> {
                    CommentEntity comment = commentRepository.findById(commentId)
                            .orElseThrow(() ->
                                    new CommentNotFoundException("Comment with id=" + commentId + " was not found"));
                    cacheManager.put(commentId, comment);

                    return comment;
                });
    }

    @Override
    public CommentInfoDTO getCommentInfoById(Long commentId, Long newsId) {
        return commentMapper.commentToCommentInfoDTO(validateCommentAndGet(commentId, newsId));
    }

    @Override
    public Page<CommentInfoDTO> searchComments(String query, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return commentRepository.searchByUsernameOrText(query, pageable)
                .map(commentMapper::commentToCommentInfoDTO);
    }

    private CommentEntity validateCommentAndGet(Long commentId, Long newsId) {
        NewsEntity news = newsInformationService.getNewsById(newsId);
        CommentEntity comment = getCommentById(commentId);

        if (!comment.getNews().getId().equals(news.getId())) {
            throw new CommentNotFoundException("Comment with id=" + commentId +
                    " does not belong to news with id=" + news.getId());
        }

        return comment;
    }
}
