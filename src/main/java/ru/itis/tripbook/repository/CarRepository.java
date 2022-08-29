package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.tripbook.model.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(nativeQuery = true,
            value = "select car.* from car " +
                    "left join account on car.user_id = account.id " +
                    "left join review on car.id = review.car_id " +
                    "where " +
                    "car.finish >= now() and " +
                    "car.is_blocked = false and " +
                    "car.is_deleted = false and " +
                    "account.is_blocked = false and " +
                    "account.is_deleted = false " +
                    "group by car.id " +
                    "order by count(review.id) desc " +
                    "limit :count")
    public List<Car> getBestOfCount(@Param("count") Long count);
}
