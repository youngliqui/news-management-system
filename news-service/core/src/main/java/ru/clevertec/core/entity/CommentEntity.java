package ru.clevertec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "news")
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @SequenceGenerator(name = "commentsIdSegGen", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentsIdSegGen")
    private Long id;

    @CreationTimestamp
    private LocalDateTime time;

    private String username;

    private String text;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private NewsEntity news;
}
