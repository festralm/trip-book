package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.tripbook.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "select user from User user " +
            "where (:user_id is null or user.id = :user_id) and " +
            "(:user_email is null or user.email = :user_email)")
    List<User> findUsersByParams(
            @Param("user_id") Long id,
            @Param("user_email") String email
    );
}
