package ru.clevertec.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.clientapi.dto.CommentCreateDTO;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.dto.CommentUpdateDTO;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.service.information.comment.CommentInformationService;
import ru.clevertec.clientapi.service.information.news.NewsInformationService;
import ru.clevertec.clientapi.service.management.comment.CommentManagementService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentInformationService commentInformationService;
    private final CommentManagementService commentManagementService;
    private final NewsInformationService newsInformationService;

    @GetMapping("/news/{newsId}/comments")
    public Page<CommentInfoDTO> getNewsComments(
            @PathVariable Long newsId,
            @RequestParam int size,
            @RequestParam int page
    ) {
        return commentInformationService.getComments(newsId, size, page);
    }

    @GetMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO getCommentById(
            @PathVariable Long newsId,
            @PathVariable Long commentId
    ) {
        return commentInformationService.getCommentInfoById(commentId);
    }

    @GetMapping("/comments/search")
    public Page<CommentInfoDTO> fullTextSearch(
            @RequestParam String text,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return commentInformationService.fullTextSearch(text, size, page);
    }

    @PostMapping("/news/{newsId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentInfoDTO createComment(
            @PathVariable Long newsId,
            @RequestBody CommentCreateDTO commentCreateDTO
    ) {
        NewsEntity news = newsInformationService.getNewsById(newsId);
        return commentManagementService.createComment(news, commentCreateDTO);
    }

    @PutMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO updateComment(
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDTO commentUpdateDTO
    ) {
        return commentManagementService.updateComment(commentId, commentUpdateDTO);
    }

    @PatchMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO patchComment(
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDTO commentUpdateDTO
    ) {
        return commentManagementService.patchComment(commentId, commentUpdateDTO);
    }

    @DeleteMapping("/news/{newsId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long newsId,
            @PathVariable Long commentId
    ) {
        commentManagementService.deleteCommentById(commentId);
    }
}