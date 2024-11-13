package ru.clevertec.core.service.information.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.exception.CommentNotFoundException;
import ru.clevertec.core.cache.CacheManager;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.mapper.CommentMapper;
import ru.clevertec.core.repository.CommentRepository;
import ru.clevertec.core.service.information.news.NewsInformationService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentInformationServiceImplTest {

    @InjectMocks
    private CommentInformationServiceImpl commentInformationService;
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private NewsInformationService newsInformationService;

    @Mock
    private CacheManager<Long, CommentEntity> cacheManager;

    private CommentEntity commentEntity;
    private NewsEntity newsEntity;
    private CommentInfoDTO commentInfoDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 1, 1);
        String testText = "test";

        commentEntity = CommentEntity.builder()
                .id(1L)
                .text(testText)
                .time(time)
                .username(testText)
                .build();

        newsEntity = NewsEntity.builder()
                .id(1L)
                .text(testText)
                .time(time)
                .comments(List.of(commentEntity))
                .build();

        commentEntity.setNews(newsEntity);

        commentInfoDTO = CommentInfoDTO.builder()
                .id(commentEntity.getId())
                .text(commentEntity.getText())
                .username(commentEntity.getUsername())
                .newsId(newsEntity.getId())
                .time(commentEntity.getTime().toString())
                .build();
    }

    @Test
    void testGetComments() {
        Long newsId = 1L;
        int size = 10;
        int page = 0;

        when(commentRepository.findAllByNews_Id(newsId, PageRequest.of(page, size)))
                .thenReturn(new PageImpl<>(Collections.singletonList(commentEntity)));
        when(commentMapper.commentToCommentInfoDTO(any(CommentEntity.class)))
                .thenReturn(commentInfoDTO);

        Page<CommentInfoDTO> result = commentInformationService.getComments(newsId, size, page);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(commentRepository).findAllByNews_Id(newsId, PageRequest.of(page, size));
    }

    @Test
    void testGetCommentByIdCached() {
        Long commentId = 1L;

        when(cacheManager.get(commentId)).thenReturn(commentEntity);

        CommentEntity result = commentInformationService.getCommentById(commentId);

        assertThat(result).isEqualTo(commentEntity);
        verify(cacheManager).get(commentId);
        verify(commentRepository, never()).findById(anyLong());
    }

    @Test
    void testGetCommentByIdNotCached() {
        Long commentId = 1L;

        when(cacheManager.get(commentId)).thenReturn(null);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));

        CommentEntity result = commentInformationService.getCommentById(commentId);

        assertThat(result).isEqualTo(commentEntity);
        verify(cacheManager).put(commentId, commentEntity);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetCommentByIncorrectId() {
        Long commentId = 100L;

        when(cacheManager.get(commentId)).thenReturn(null);
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () ->
                commentInformationService.getCommentById(commentId));
    }

    @Test
    void testGetCommentInfoById() {
        Long commentId = 1L;

        when(newsInformationService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(cacheManager.get(commentId)).thenReturn(commentEntity);
        when(commentMapper.commentToCommentInfoDTO(any(CommentEntity.class))).thenReturn(commentInfoDTO);

        CommentInfoDTO result = commentInformationService.getCommentInfoById(commentId, newsEntity.getId());

        assertThat(result).isEqualTo(commentInfoDTO);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetCommentInfoByInvalidNews() {
        Long invalidNewsId = 100L;

        when(newsInformationService.getNewsById(invalidNewsId)).thenReturn(new NewsEntity());
        when(cacheManager.get(anyLong())).thenReturn(commentEntity);

        assertThrows(CommentNotFoundException.class, () ->
                commentInformationService.getCommentInfoById(commentEntity.getId(), invalidNewsId));
    }

    @Test
    void testSearchComments() {
        String query = "test";
        int page = 0;
        int size = 10;

        when(commentRepository.searchByUsernameOrText(eq(query), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(commentEntity)));
        when(commentMapper.commentToCommentInfoDTO(any(CommentEntity.class)))
                .thenReturn(commentInfoDTO);

        Page<CommentInfoDTO> result = commentInformationService.searchComments(query, size, page);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(commentRepository).searchByUsernameOrText(query, PageRequest.of(page, size));
    }
}