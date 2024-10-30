package ru.clevertec.clientapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.clientapi.entity.NewsEntity;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    @Query("SELECT n FROM NewsEntity n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(n.text) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<NewsEntity> searchByText(@Param("text") String text);
}
