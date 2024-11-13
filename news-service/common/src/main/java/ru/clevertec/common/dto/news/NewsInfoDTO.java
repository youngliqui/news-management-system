package ru.clevertec.common.dto.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsInfoDTO {
    private Long id;
    private String time;
    private String title;
    private String text;
    private Integer commentsCount;
}
