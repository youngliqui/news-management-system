package ru.clevertec.clientapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDTO {
    private String username;
    private String text;
}
