package ru.clevertec.common.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentPatchDTO {
    private String username;

    @Size(max = 500, message = "Text must not exceed 500 characters")
    private String text;
}
