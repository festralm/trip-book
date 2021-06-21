package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tripbook.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
