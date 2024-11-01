package ru.clevertec.clientapi.mapper;

import org.mapstruct.*;
import ru.clevertec.clientapi.dto.comment.CommentCreateDTO;
import ru.clevertec.clientapi.dto.comment.CommentInfoDTO;
import ru.clevertec.clientapi.dto.comment.CommentPatchDTO;
import ru.clevertec.clientapi.dto.comment.CommentUpdateDTO;
import ru.clevertec.clientapi.entity.CommentEntity;
import ru.clevertec.clientapi.entity.NewsEntity;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "news.id", target = "newsId")
    CommentInfoDTO commentToCommentInfoDTO(CommentEntity commentEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(source = "commentCreateDTO.text", target = "text")
    CommentEntity commentCreateDTOToComment(CommentCreateDTO commentCreateDTO, NewsEntity news);

    void updateCommentFromDTO(@MappingTarget CommentEntity comment, CommentUpdateDTO commentUpdateDTO);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void patchCommentFromDTO(@MappingTarget CommentEntity comment, CommentPatchDTO commentPatchDTO);
}
