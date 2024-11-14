package ru.clevertec.common.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDTO {
    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Text must not be empty")
    @Size(max = 500, message = "Text must not exceed 500 characters")
    private String text;
}
