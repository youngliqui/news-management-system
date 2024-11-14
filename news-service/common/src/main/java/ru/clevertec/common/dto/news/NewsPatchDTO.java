package ru.clevertec.common.dto.news;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsPatchDTO {
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String text;
}
