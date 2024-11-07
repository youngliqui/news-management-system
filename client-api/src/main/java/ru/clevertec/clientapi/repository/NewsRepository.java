package ru.clevertec.clientapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.clientapi.entity.NewsEntity;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    @Query(value = "SELECT * FROM news n WHERE n.title ILIKE %:query% OR n.text ILIKE %:query%", nativeQuery = true)
    Page<NewsEntity> searchByTitleOrText(@Param("query") String query, Pageable pageable);
}
