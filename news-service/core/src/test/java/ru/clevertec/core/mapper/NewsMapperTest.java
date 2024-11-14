package ru.clevertec.core.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class NewsMapperTest {
    private NewsMapper newsMapper;

    @BeforeEach
    void setUp() {
        newsMapper = Mappers.getMapper(NewsMapper.class);
    }

    @Test
    void testNewsToNewsInfoDTO() {
        CommentEntity comment = CommentEntity.builder()
                .id(1L)
                .build();

        NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .text("text")
                .title("title")
                .comments(Collections.singletonList(comment))
                .build();

        NewsInfoDTO expectedNewsDTO = NewsInfoDTO.builder()
                .id(newsEntity.getId())
                .text(newsEntity.getText())
                .title(newsEntity.getTitle())
                .commentsCount(newsEntity.getComments().size())
                .build();


        NewsInfoDTO result = newsMapper.newsToNewsInfoDTO(newsEntity);


        assertThat(result).isEqualTo(expectedNewsDTO);
    }

    @Test
    void testNewsCreateDTOToNews() {
        NewsCreateDTO newsCreateDTO = NewsCreateDTO.builder()
                .text("text")
                .title("title")
                .build();
        NewsEntity expectedNews = NewsEntity.builder()
                .title(newsCreateDTO.getTitle())
                .text(newsCreateDTO.getText())
                .comments(new ArrayList<>())
                .build();

        NewsEntity result = newsMapper.newsCreateDTOToNews(newsCreateDTO);

        assertThat(result).isEqualTo(expectedNews);
    }

    @Test
    void testUpdateNewsFromDTO() {
        NewsEntity updatedNews = NewsEntity.builder()
                .id(1L)
                .build();
        NewsUpdateDTO newsUpdateDTO = NewsUpdateDTO.builder()
                .text("new")
                .title("new")
                .build();
        NewsEntity expectedNews = NewsEntity.builder()
                .id(updatedNews.getId())
                .text(newsUpdateDTO.getText())
                .title(newsUpdateDTO.getTitle())
                .build();

        newsMapper.updateNewsFromDTO(updatedNews, newsUpdateDTO);

        assertThat(updatedNews).isEqualTo(expectedNews);
    }

    @Test
    void testPatchNewsFromDTO() {
        NewsEntity patchedNews = NewsEntity.builder()
                .id(1L)
                .title("old")
                .text("old")
                .build();
        NewsPatchDTO newsPatchDTO = NewsPatchDTO.builder()
                .text("new")
                .build();
        NewsEntity expectedNews = NewsEntity.builder()
                .id(patchedNews.getId())
                .text(newsPatchDTO.getText())
                .title(patchedNews.getTitle())
                .build();

        newsMapper.patchNewsFromDTO(patchedNews, newsPatchDTO);

        assertThat(patchedNews).isEqualTo(expectedNews);
    }
}