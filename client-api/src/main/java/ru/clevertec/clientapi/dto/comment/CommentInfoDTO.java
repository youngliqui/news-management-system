package ru.clevertec.clientapi.dto.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentInfoDTO {
    private Long id;
    private String time;
    private String username;
    private String text;
    private Long newsId;
}
