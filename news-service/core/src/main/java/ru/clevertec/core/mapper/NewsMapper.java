package ru.clevertec.core.mapper;

import org.mapstruct.*;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;
import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(source = "comments", target = "commentsCount", qualifiedByName = "mapCommentsToCount")
    NewsInfoDTO newsToNewsInfoDTO(NewsEntity newsEntity);

    @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())")
    NewsEntity newsCreateDTOToNews(NewsCreateDTO newsCreateDTO);

    void updateNewsFromDTO(@MappingTarget NewsEntity newsEntity, NewsUpdateDTO newsUpdateDTO);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void patchNewsFromDTO(@MappingTarget NewsEntity newsEntity, NewsPatchDTO newsPatchDTO);

    @Named("mapCommentsToCount")
    static Integer mapCommentsToCount(List<CommentEntity> comments) {
        return comments.size();
    }
}
