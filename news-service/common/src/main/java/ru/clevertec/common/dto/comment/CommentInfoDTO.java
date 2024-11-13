package ru.clevertec.common.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfoDTO {
    private Long id;
    private String time;
    private String username;
    private String text;
    private Long newsId;
}
