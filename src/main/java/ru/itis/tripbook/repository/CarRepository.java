package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.tripbook.model.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(nativeQuery = true,
            value = "select * from car " +
                    "where " +
                    "car.finish >= now() and " +
                    "car.is_blocked = false and " +
                    "car.is_deleted = false " +
                    "order by car.rating desc " +
                    "limit :count")
    public List<Car> getBestOfCount(@Param("count") Long count);
}
