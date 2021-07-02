package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.CarBrand;
import ru.itis.tripbook.model.CarModel;
import ru.itis.tripbook.model.Role;

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

    @Query(value = "select car from Car car " +
            "where " +
            "(:car_id is null or car.id = :car_id) and " +
            "(:name is null or car.name = :name) and " +
            "(:brand is null or car.brand = :brand) and " +
            "(:model is null or car.model = :model) and " +
            "(:is_deleted is null or car.isDeleted = :is_deleted) and " +
            "(:is_blocked is null or car.isBlocked = :is_blocked) ")
    List<Car> findCarsByParams(
            @Param("car_id") Long id,
            @Param("name") String name,
            @Param("brand") CarBrand brand,
            @Param("model") CarModel model,
            @Param("is_deleted") Boolean isDeleted,
            @Param("is_blocked") Boolean isBlocked
    );
}
