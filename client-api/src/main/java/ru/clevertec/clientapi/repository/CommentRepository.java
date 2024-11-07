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
    @Query(value = "SELECT * FROM comments c WHERE c.username ILIKE %:query% OR c.text ILIKE %:query%", nativeQuery = true)
    Page<CommentEntity> searchByUsernameOrText(@Param("query") String query, Pageable pageable);

    Page<CommentEntity> findAllByNews_Id(Long newsId, Pageable pageable);
}
