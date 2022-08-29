package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tripbook.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
