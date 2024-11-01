package ru.clevertec.clientapi.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Text must not be empty")
    @Size(max = 500, message = "Text must not exceed 500 characters")
    private String text;
}
