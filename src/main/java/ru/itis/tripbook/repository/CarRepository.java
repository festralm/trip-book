package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tripbook.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
