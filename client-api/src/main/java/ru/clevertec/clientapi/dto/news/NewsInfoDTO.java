package ru.clevertec.clientapi.dto.news;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewsInfoDTO {
    private Long id;
    private String time;
    private String title;
    private String text;
    private Integer commentsCount;
}
