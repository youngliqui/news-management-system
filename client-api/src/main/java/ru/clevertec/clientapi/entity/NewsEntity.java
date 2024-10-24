package ru.clevertec.clientapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class NewsEntity {

    @Id
    @SequenceGenerator(name = "newsIdSeqGen", sequenceName = "news_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsIdSeqGen")
    private Long id;

    @CreatedDate
    private LocalDateTime time;

    private String title;

    private String text;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
