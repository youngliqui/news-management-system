package ru.clevertec.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.api.feign_client.access.AccessControlService;
import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.service.information.comment.CommentInformationService;
import ru.clevertec.core.service.management.comment.CommentManagementService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentInformationService commentInformationService;

    @Mock
    private CommentManagementService commentManagementService;

    @Mock
    private AccessControlService accessControlService;

    private CommentEntity commentEntity;
    private CommentInfoDTO commentInfoDTO;

    @BeforeEach
    void setUp() {
        commentEntity = CommentEntity.builder()
                .id(1L)
                .text("text")
                .username("username")
                .build();
        commentInfoDTO = CommentInfoDTO.builder()
                .id(commentEntity.getId())
                .text(commentEntity.getText())
                .username(commentEntity.getUsername())
                .build();
    }

    @Nested
    class CommentControllerGetTests {
        @Test
        void testGetNewsComments() {
            Long newsId = 1L;
            int size = 10;
            int page = 0;
            Page<CommentInfoDTO> expectedPage = new PageImpl<>(
                    List.of(commentInfoDTO), PageRequest.of(page, size), 1);

            when(commentInformationService.getComments(newsId, size, page))
                    .thenReturn(expectedPage);


            Page<CommentInfoDTO> result = commentController.getNewsComments(newsId, size, page);

            assertThat(result).isEqualTo(expectedPage);
            verify(commentInformationService).getComments(newsId, size, page);
        }

        @Test
        void testGetCommentById() {
            Long newsId = 1L;
            Long commentId = 1L;

            when(commentInformationService.getCommentInfoById(commentId, newsId))
                    .thenReturn(commentInfoDTO);

            CommentInfoDTO result = commentController.getCommentById(newsId, commentId);

            assertThat(result).isEqualTo(commentInfoDTO);
            verify(commentInformationService).getCommentInfoById(commentId, newsId);
        }

        @Test
        void testFullTextSearch() {
            String query = "text";
            int size = 10;
            int page = 0;
            Page<CommentInfoDTO> expectedPage = new PageImpl<>(
                    List.of(commentInfoDTO), PageRequest.of(page, size), 1
            );

            when(commentInformationService.searchComments(query, size, page)).thenReturn(expectedPage);

            Page<CommentInfoDTO> result = commentController.fullTextSearch(query, size, page);

            assertThat(result).isEqualTo(expectedPage);
            verify(commentInformationService).searchComments(query, size, page);
        }
    }

    @Nested
    class CommentControllerPostTests {
        @Test
        void testCreateComment() {
            Long newsId = 1L;
            String bearerToken = "Bearer token";
            CommentCreateDTO commentCreateDTO = new CommentCreateDTO();

            doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));
            when(commentManagementService.createComment(anyLong(), any(CommentCreateDTO.class))).thenReturn(commentInfoDTO);

            CommentInfoDTO result = commentController.createComment(bearerToken, newsId, commentCreateDTO);

            assertThat(result).isEqualTo(commentInfoDTO);
            verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));
            verify(commentManagementService).createComment(newsId, commentCreateDTO);
        }
    }

    @Nested
    class CommentControllerPutPatchTests {
        @Test
        void testUpdateComment() {
            Long newsId = 1L;
            Long commentId = 1L;
            String bearerToken = "Bearer token";
            CommentUpdateDTO commentUpdateDTO = new CommentUpdateDTO();

            doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));
            when(commentManagementService.updateComment(anyLong(), anyLong(), any(CommentUpdateDTO.class)))
                    .thenReturn(commentInfoDTO);

            CommentInfoDTO result = commentController.updateComment(bearerToken, newsId, commentId, commentUpdateDTO);

            assertThat(result).isEqualTo(commentInfoDTO);
            verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));
            verify(commentManagementService).updateComment(commentId, newsId, commentUpdateDTO);
        }

        @Test
        void testPatchComment() {
            Long newsId = 1L;
            Long commentId = 1L;
            String bearerToken = "Bearer token";
            CommentPatchDTO commentPatchDTO = new CommentPatchDTO();

            doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));
            when(commentManagementService.patchComment(anyLong(), anyLong(), any(CommentPatchDTO.class)))
                    .thenReturn(commentInfoDTO);

            CommentInfoDTO result = commentController.patchComment(bearerToken, newsId, commentId, commentPatchDTO);

            assertThat(result).isEqualTo(commentInfoDTO);
            verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));
            verify(commentManagementService).patchComment(commentId, newsId, commentPatchDTO);
        }
    }

    @Nested
    class CommentControllerDeleteTests {
        @Test
        void testDeleteComment() {
            Long newsId = 1L;
            Long commentId = 1L;
            String bearerToken = "Bearer token";

            doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

            assertDoesNotThrow(() ->
                    commentController.deleteComment(bearerToken, newsId, commentId)
            );

            verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));
            verify(commentManagementService).deleteCommentById(commentId, newsId);
        }
    }
}