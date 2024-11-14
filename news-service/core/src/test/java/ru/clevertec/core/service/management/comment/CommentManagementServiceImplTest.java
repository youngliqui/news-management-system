package ru.clevertec.core.service.management.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import ru.clevertec.core.service.information.news.NewsInformationServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentManagementServiceImplTest {

    @InjectMocks
    private CommentManagementServiceImpl commentManagementService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private NewsInformationServiceImpl newsInformationService;

    @Mock
    private CacheManager<Long, CommentEntity> cacheManager;

    private CommentEntity commentEntity;
    private NewsEntity newsEntity;
    private CommentInfoDTO commentInfoDTO;

    @BeforeEach
    void setUp() {
        String testText = "test";
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 1, 1);

        newsEntity = NewsEntity.builder()
                .id(1L)
                .time(time)
                .title(testText)
                .text(testText)
                .build();

        commentEntity = CommentEntity.builder()
                .id(1L)
                .time(time)
                .username(testText)
                .text(testText)
                .build();

        commentInfoDTO = CommentInfoDTO.builder()
                .id(commentEntity.getId())
                .username(commentEntity.getUsername())
                .text(commentEntity.getText())
                .time(time.toString())
                .build();

        commentEntity.setNews(newsEntity);
        newsEntity.setComments(Collections.singletonList(commentEntity));
    }

    @Test
    void testCreateComment() {
        Long newsId = 1L;

        when(newsInformationService.getNewsById(newsId)).thenReturn(newsEntity);
        when(commentMapper.commentCreateDTOToComment(any(CommentCreateDTO.class), eq(newsEntity)))
                .thenReturn(commentEntity);
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        when(commentMapper.commentToCommentInfoDTO(commentEntity)).thenReturn(commentInfoDTO);

        CommentInfoDTO result = commentManagementService.createComment(newsId, new CommentCreateDTO());

        assertThat(result).isEqualTo(commentInfoDTO);
        verify(commentRepository).save(commentEntity);
        verify(cacheManager).put(commentEntity.getId(), commentEntity);
    }

    @Test
    void testUpdateComment() {
        Long newsId = 1L;
        Long commentId = 1L;
        CommentUpdateDTO commentUpdateDTO = new CommentUpdateDTO();

        when(newsInformationService.getNewsById(newsId)).thenReturn(newsEntity);
        when(cacheManager.get(commentId)).thenReturn(commentEntity);
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);
        when(commentMapper.commentToCommentInfoDTO(any(CommentEntity.class))).thenReturn(commentInfoDTO);

        CommentInfoDTO result = commentManagementService.updateComment(commentId, newsId, commentUpdateDTO);

        assertThat(result).isEqualTo(commentInfoDTO);
        verify(commentMapper).updateCommentFromDTO(commentEntity, commentUpdateDTO);
        verify(commentRepository).save(any(CommentEntity.class));
        verify(cacheManager).put(commentId, commentEntity);
    }

    @Test
    void testPatchComment() {
        Long newsId = 1L;
        Long commentId = 1L;
        CommentPatchDTO commentPatchDTO = new CommentPatchDTO();

        when(newsInformationService.getNewsById(newsId)).thenReturn(newsEntity);
        when(cacheManager.get(commentId)).thenReturn(commentEntity);
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);
        when(commentMapper.commentToCommentInfoDTO(any(CommentEntity.class))).thenReturn(commentInfoDTO);

        CommentInfoDTO result = commentManagementService.patchComment(commentId, newsId, commentPatchDTO);

        assertThat(result).isEqualTo(commentInfoDTO);
        verify(commentMapper).patchCommentFromDTO(commentEntity, commentPatchDTO);
        verify(commentRepository).save(any(CommentEntity.class));
        verify(cacheManager).put(commentId, commentEntity);
    }

    @Test
    void testDeleteCommentById() {
        Long commentId = 1L;
        Long newsId = 1L;

        when(newsInformationService.getNewsById(newsId)).thenReturn(newsEntity);
        when(cacheManager.get(commentId)).thenReturn(commentEntity);

        commentManagementService.deleteCommentById(commentId, newsId);

        verify(commentRepository).delete(commentEntity);
        verify(cacheManager).remove(commentId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdateCommentWithInvalidNewsId() {
        Long invalidNewsId = 100L;

        when(newsInformationService.getNewsById(invalidNewsId)).thenReturn(new NewsEntity());
        when(cacheManager.get(anyLong())).thenReturn(commentEntity);

        assertThrows(CommentNotFoundException.class, () ->
                commentManagementService.updateComment(1L, invalidNewsId, new CommentUpdateDTO()));
    }

    @Test
    void testGetCommentByCorrectId() {
        Long commentId = 1L;
        Long newsId = 1L;

        when(newsInformationService.getNewsById(newsId)).thenReturn(newsEntity);
        when(cacheManager.get(commentId)).thenReturn(null);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);
        when(commentMapper.commentToCommentInfoDTO(commentEntity)).thenReturn(commentInfoDTO);

        CommentInfoDTO result = commentManagementService.updateComment(commentId, newsId, new CommentUpdateDTO());

        assertThat(result).isEqualTo(commentInfoDTO);
        verify(commentRepository).findById(commentId);
        verify(commentRepository).save(commentEntity);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetCommentByIncorrectId() {
        Long incorrectCommentId = 100L;

        when(newsInformationService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(cacheManager.get(incorrectCommentId)).thenReturn(null);
        when(commentRepository.findById(incorrectCommentId)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () ->
                commentManagementService.updateComment(incorrectCommentId, 1L, new CommentUpdateDTO()));
    }
}