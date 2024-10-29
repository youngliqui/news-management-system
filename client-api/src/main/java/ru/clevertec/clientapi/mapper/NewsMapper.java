package ru.clevertec.clientapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.entity.CommentEntity;
import ru.clevertec.clientapi.entity.NewsEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(source = "comments", target = "commentsCount", qualifiedByName = "mapCommentsToCount")
    NewsInfoDTO newsToNewsInfoDTO(NewsEntity newsEntity);

    @Named("mapCommentsToCount")
    static Integer mapCommentsToCount(List<CommentEntity> comments) {
        return comments.size();
    }
}
