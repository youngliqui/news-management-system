package ru.clevertec.clientapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.clientapi.dto.comment.CommentCreateDTO;
import ru.clevertec.clientapi.dto.comment.CommentInfoDTO;
import ru.clevertec.clientapi.dto.comment.CommentPatchDTO;
import ru.clevertec.clientapi.dto.comment.CommentUpdateDTO;
import ru.clevertec.clientapi.service.information.comment.CommentInformationService;
import ru.clevertec.clientapi.service.management.comment.CommentManagementService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentInformationService commentInformationService;
    private final CommentManagementService commentManagementService;

    @GetMapping("/news/{newsId}/comments")
    public Page<CommentInfoDTO> getNewsComments(
            @PathVariable Long newsId,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return commentInformationService.getComments(newsId, size, page);
    }

    @GetMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO getCommentById(
            @PathVariable Long newsId,
            @PathVariable Long commentId
    ) {
        return commentInformationService.getCommentInfoById(commentId, newsId);
    }

    @GetMapping("/comments/search")
    public Page<CommentInfoDTO> fullTextSearch(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return commentInformationService.searchComments(query, size, page);
    }

    @PostMapping("/news/{newsId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentInfoDTO createComment(
            @PathVariable Long newsId,
            @Valid @RequestBody CommentCreateDTO commentCreateDTO
    ) {
        return commentManagementService.createComment(newsId, commentCreateDTO);
    }

    @PutMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO updateComment(
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateDTO commentUpdateDTO
    ) {
        return commentManagementService.updateComment(commentId, newsId, commentUpdateDTO);
    }

    @PatchMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO patchComment(
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentPatchDTO commentPatchDTO
    ) {
        return commentManagementService.patchComment(commentId, newsId, commentPatchDTO);
    }

    @DeleteMapping("/news/{newsId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long newsId,
            @PathVariable Long commentId
    ) {
        commentManagementService.deleteCommentById(commentId, newsId);
    }
}
