package ru.clevertec.clientapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    private String username;
    private String text;
}
