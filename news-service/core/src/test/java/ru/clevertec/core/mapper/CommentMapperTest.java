package ru.clevertec.core.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CommentMapperTest {
    private CommentMapper commentMapper;

    @BeforeEach
    void setUp() {
        commentMapper = Mappers.getMapper(CommentMapper.class);
    }

    @Test
    void testCommentToCommentInfoDTO() {
        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .text("test_text")
                .username("test_username")
                .build();
        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .text("test")
                .build();
        commentEntity.setNews(newsEntity);

        CommentInfoDTO expectedCommentInfoDTO = CommentInfoDTO.builder()
                .id(commentEntity.getId())
                .text(commentEntity.getText())
                .username(commentEntity.getUsername())
                .newsId(newsEntity.getId())
                .build();


        CommentInfoDTO result = commentMapper.commentToCommentInfoDTO(commentEntity);


        assertThat(result).isEqualTo(expectedCommentInfoDTO);
    }

    @Test
    void testCommentCreateDTOToComment() {
        String testText = "test";

        CommentCreateDTO commentCreateDTO = CommentCreateDTO.builder()
                .text(testText)
                .username(testText)
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .text(testText)
                .build();

        CommentEntity expectedCommentEntity = CommentEntity.builder()
                .text(testText)
                .username(testText)
                .news(newsEntity)
                .build();


        CommentEntity result = commentMapper.commentCreateDTOToComment(commentCreateDTO, newsEntity);


        assertThat(result).isEqualTo(expectedCommentEntity);
    }

    @Test
    void testUpdateCommentFromDTO() {
        CommentEntity updatedComment = CommentEntity.builder()
                .id(1L)
                .time(LocalDateTime.of(2000, 1, 1, 1, 1))
                .text("old")
                .build();
        CommentUpdateDTO commentUpdateDTO = CommentUpdateDTO.builder()
                .text("new")
                .username("new")
                .build();

        CommentEntity expectedComment = CommentEntity.builder()
                .id(updatedComment.getId())
                .time(updatedComment.getTime())
                .text(commentUpdateDTO.getText())
                .username(commentUpdateDTO.getUsername())
                .build();


        commentMapper.updateCommentFromDTO(updatedComment, commentUpdateDTO);


        assertThat(updatedComment).isEqualTo(expectedComment);
    }

    @Test
    void testPatchCommentFromDTO() {
        CommentEntity patchedComment = CommentEntity.builder()
                .id(1L)
                .time(LocalDateTime.of(2000, 1, 1, 1, 1))
                .text("old")
                .username("old")
                .build();
        CommentPatchDTO commentPatchDTO = CommentPatchDTO.builder()
                .text("new")
                .build();

        CommentEntity expectedComment = CommentEntity.builder()
                .id(patchedComment.getId())
                .time(patchedComment.getTime())
                .text(commentPatchDTO.getText())
                .username(patchedComment.getUsername())
                .build();


        commentMapper.patchCommentFromDTO(patchedComment, commentPatchDTO);


        assertThat(patchedComment).isEqualTo(expectedComment);
    }
}