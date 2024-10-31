package ru.clevertec.clientapi.service.information.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.entity.CommentEntity;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.exception.CommentNotFoundException;
import ru.clevertec.clientapi.mapper.CommentMapper;
import ru.clevertec.clientapi.repository.CommentRepository;
import ru.clevertec.clientapi.service.information.news.NewsInformationService;

@Service
@RequiredArgsConstructor
public class CommentInformationServiceImpl implements CommentInformationService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsInformationService newsInformationService;

    @Override
    public Page<CommentInfoDTO> getComments(Long newsId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return commentRepository.findAllByNews_Id(newsId, pageable)
                .map(commentMapper::commentToCommentInfoDTO);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment with id=" + commentId + " was not found"));
    }

    @Override
    public CommentInfoDTO getCommentInfoById(Long commentId, Long newsId) {
        return commentMapper.commentToCommentInfoDTO(validateCommentAndGet(commentId, newsId));
    }

    @Override
    public Page<CommentInfoDTO> fullTextSearch(String text, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        return commentRepository.searchByText(text, pageable)
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
