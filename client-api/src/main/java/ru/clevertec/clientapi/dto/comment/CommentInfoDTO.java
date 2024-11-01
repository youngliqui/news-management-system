package ru.clevertec.clientapi.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInfoDTO {
    private Long id;
    private String time;
    private String username;
    private String text;
    private Long newsId;
}
