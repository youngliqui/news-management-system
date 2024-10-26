package ru.clevertec.clientapi.service.management.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clientapi.dto.CommentCreateDTO;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.dto.CommentUpdateDTO;
import ru.clevertec.clientapi.entity.NewsEntity;
import ru.clevertec.clientapi.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentManagementServiceImpl implements CommentManagementService {
    private CommentRepository commentRepository;


    @Override
    public CommentInfoDTO createComment(NewsEntity news, CommentCreateDTO commentCreateDTO) {
        return null;
    }

    @Override
    public CommentInfoDTO updateComment(Long commentId, CommentUpdateDTO commentUpdateDTO) {
        return null;
    }

    @Override
    public CommentInfoDTO patchComment(Long commentId, CommentUpdateDTO commentUpdateDTO) {
        return null;
    }

    @Override
    public void deleteCommentById(Long commentId) {

    }
}
