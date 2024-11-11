package ru.clevertec.security.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.security.core.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long userId);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
