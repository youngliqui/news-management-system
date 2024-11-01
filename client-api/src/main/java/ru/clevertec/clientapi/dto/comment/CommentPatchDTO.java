package ru.clevertec.clientapi.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPatchDTO {
    private String username;

    @Size(max = 500, message = "Text must not exceed 500 characters")
    private String text;
}
