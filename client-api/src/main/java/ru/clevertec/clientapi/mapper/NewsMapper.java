package ru.clevertec.clientapi.mapper;

import org.mapstruct.*;
import ru.clevertec.clientapi.dto.NewsCreateDTO;
import ru.clevertec.clientapi.dto.NewsInfoDTO;
import ru.clevertec.clientapi.dto.NewsUpdateDTO;
import ru.clevertec.clientapi.entity.CommentEntity;
import ru.clevertec.clientapi.entity.NewsEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(source = "comments", target = "commentsCount", qualifiedByName = "mapCommentsToCount")
    NewsInfoDTO newsToNewsInfoDTO(NewsEntity newsEntity);

    NewsEntity newsCreateDTOToNews(NewsCreateDTO newsCreateDTO);

    void updateNewsFromDTO(@MappingTarget NewsEntity newsEntity, NewsUpdateDTO newsUpdateDTO);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void patchNewsFromDTO(@MappingTarget NewsEntity newsEntity, NewsUpdateDTO newsUpdateDTO);

    @Named("mapCommentsToCount")
    static Integer mapCommentsToCount(List<CommentEntity> comments) {
        return comments.size();
    }
}
