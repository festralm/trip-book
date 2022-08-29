package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tripbook.model.CarBrand;

public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {

}
