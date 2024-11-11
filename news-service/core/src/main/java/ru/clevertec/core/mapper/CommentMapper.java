package ru.clevertec.core.mapper;

import org.mapstruct.*;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.common.dto.comment.CommentCreateDTO;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.common.dto.comment.CommentPatchDTO;
import ru.clevertec.common.dto.comment.CommentUpdateDTO;

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
