package ru.clevertec.clientapi.dto.news;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewsPatchDTO {
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String username;
}
