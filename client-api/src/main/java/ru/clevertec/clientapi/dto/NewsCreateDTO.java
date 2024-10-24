package ru.clevertec.clientapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsCreateDTO {
    private String title;
    private String text;
}
