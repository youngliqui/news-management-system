package ru.clevertec.clientapi.dto.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsInfoDTO {
    private Long id;
    private String time;
    private String title;
    private String text;
    private Integer commentsCount;
}
