package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tripbook.model.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
