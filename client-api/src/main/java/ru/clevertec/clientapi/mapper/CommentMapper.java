package ru.clevertec.clientapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.clientapi.dto.CommentInfoDTO;
import ru.clevertec.clientapi.entity.CommentEntity;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "news.id", target = "newsId")
    CommentInfoDTO commentToCommentInfoDTO(CommentEntity commentEntity);
}
