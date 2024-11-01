package ru.clevertec.clientapi.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsUpdateDTO {
    @NotBlank(message = "Title must not be empty")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Text must not be empty")
    private String text;
}
