package ru.clevertec.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.feign_client.access.AccessControlService;
import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;
import ru.clevertec.core.service.information.comment.CommentInformationService;
import ru.clevertec.core.service.management.comment.CommentManagementService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentInformationService commentInformationService;
    private final CommentManagementService commentManagementService;
    private final AccessControlService accessControlService;

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
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @Valid @RequestBody CommentCreateDTO commentCreateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        return commentManagementService.createComment(newsId, commentCreateDTO);
    }

    @PutMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO updateComment(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateDTO commentUpdateDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        return commentManagementService.updateComment(commentId, newsId, commentUpdateDTO);
    }

    @PatchMapping("/news/{newsId}/comments/{commentId}")
    public CommentInfoDTO patchComment(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentPatchDTO commentPatchDTO
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        return commentManagementService.patchComment(commentId, newsId, commentPatchDTO);
    }

    @DeleteMapping("/news/{newsId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long newsId,
            @PathVariable Long commentId
    ) {
        accessControlService.checkAccess(bearerToken, List.of("ADMIN", "SUBSCRIBER"));

        commentManagementService.deleteCommentById(commentId, newsId);
    }
}
