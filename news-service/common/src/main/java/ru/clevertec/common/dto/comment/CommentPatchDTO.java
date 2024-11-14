package ru.clevertec.common.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPatchDTO {
    private String username;

    @Size(max = 500, message = "Text must not exceed 500 characters")
    private String text;
}
