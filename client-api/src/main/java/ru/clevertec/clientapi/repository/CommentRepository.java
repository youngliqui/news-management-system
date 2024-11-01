package ru.clevertec.clientapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.clientapi.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c WHERE " +
            "(:username IS NULL OR LOWER(c.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
            "(:text IS NULL OR LOWER(c.text) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<CommentEntity> searchByParameters(@Param("username") String username,
                                           @Param("text") String text,
                                           Pageable pageable);

    Page<CommentEntity> findAllByNews_Id(Long newsId, Pageable pageable);
}
