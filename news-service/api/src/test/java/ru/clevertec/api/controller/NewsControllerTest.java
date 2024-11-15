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
import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.core.service.information.news.NewsInformationService;
import ru.clevertec.core.service.management.news.NewsManagementService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsControllerTest {
    @InjectMocks
    private NewsController newsController;

    @Mock
    private NewsInformationService newsInformationService;

    @Mock
    private NewsManagementService newsManagementService;

    @Mock
    private AccessControlService accessControlService;


    private NewsEntity newsEntity;
    private NewsInfoDTO newsInfoDTO;

    @BeforeEach
    void setUp() {
        newsEntity = NewsEntity.builder()
                .id(1L)
                .text("text")
                .title("title")
                .build();
        newsInfoDTO = NewsInfoDTO.builder()
                .id(newsEntity.getId())
                .title(newsEntity.getTitle())
                .text(newsEntity.getTitle())
                .build();
    }

    @Nested
    class NewsControllerGetTests {
        @Test
        void testGetAllNews() {
            int size = 10;
            int page = 0;
            Page<NewsInfoDTO> expectedPage = new PageImpl<>(
                    Collections.singletonList(newsInfoDTO), PageRequest.of(page, size), 1
            );

            when(newsInformationService.getAll(10, 0)).thenReturn(expectedPage);

            Page<NewsInfoDTO> result = newsController.getAllNews(size, page);

            assertThat(result).isEqualTo(expectedPage);
            verify(newsInformationService).getAll(size, page);
        }

        @Test
        void testGetNewsById() {
            Long newsId = 1L;

            when(newsInformationService.getNewsInfoById(newsId)).thenReturn(newsInfoDTO);

            NewsInfoDTO result = newsController.getNewsById(newsId);

            assertThat(result).isEqualTo(newsInfoDTO);
            verify(newsInformationService).getNewsInfoById(newsId);
        }

        @Test
        void testSearch() {
            String query = "text";
            int size = 10;
            int page = 0;
            Page<NewsInfoDTO> expectedPage = new PageImpl<>(
                    Collections.singletonList(newsInfoDTO), PageRequest.of(page, size), 1
            );

            when(newsInformationService.searchNews(query, size, page)).thenReturn(expectedPage);

            Page<NewsInfoDTO> result = newsController.search(query, size, page);

            assertThat(result).isEqualTo(expectedPage);
            verify(newsInformationService).searchNews(query, size, page);
        }

        @Nested
        class NewsControllerPostTests {
            @Test
            void testCreateNews() {
                String bearerToken = "Bearer token";
                NewsCreateDTO newsCreateDTO = new NewsCreateDTO();

                doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

                when(newsManagementService.createNews(any(NewsCreateDTO.class))).thenReturn(newsInfoDTO);

                NewsInfoDTO result = newsController.createNews(bearerToken, newsCreateDTO);

                assertThat(result).isEqualTo(newsInfoDTO);
                verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));
                verify(newsManagementService).createNews(newsCreateDTO);
            }
        }

        @Nested
        class NewsControllerPutPatchTests {
            @Test
            void testUpdateNews() {
                Long newsId = 1L;
                String bearerToken = "Bearer token";
                NewsUpdateDTO newsUpdateDTO = new NewsUpdateDTO();

                doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

                when(newsManagementService.updateNews(anyLong(), any(NewsUpdateDTO.class))).thenReturn(newsInfoDTO);

                NewsInfoDTO result = newsController.updateNews(bearerToken, newsId, newsUpdateDTO);

                assertThat(result).isEqualTo(newsInfoDTO);
                verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));
                verify(newsManagementService).updateNews(newsId, newsUpdateDTO);
            }

            @Test
            void testPatchNews() {
                Long newsId = 1L;
                String bearerToken = "Bearer token";
                NewsPatchDTO newsPatchDTO = new NewsPatchDTO();

                doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

                when(newsManagementService.patchNews(anyLong(), any(NewsPatchDTO.class))).thenReturn(newsInfoDTO);

                NewsInfoDTO result = newsController.patchNews(bearerToken, newsId, newsPatchDTO);

                assertThat(result).isEqualTo(newsInfoDTO);
                verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));
                verify(newsManagementService).patchNews(newsId, newsPatchDTO);
            }
        }

        @Nested
        class NewsControllerDeleteTests {
            @Test
            void testDeleteNews() {
                Long newsId = 1L;
                String bearerToken = "Bearer token";

                doNothing().when(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));

                assertDoesNotThrow(() ->
                        newsController.deleteNews(bearerToken, newsId)
                );

                verify(accessControlService).checkAccess(bearerToken, List.of("ADMIN", "JOURNALIST"));
                verify(newsManagementService).deleteNewsById(newsId);
            }
        }
    }
}