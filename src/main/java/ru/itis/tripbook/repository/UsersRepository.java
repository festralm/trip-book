package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tripbook.model.User;

public interface UsersRepository extends JpaRepository<User, Long> {

}
