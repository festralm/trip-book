package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserService userService;

    @Override
    public Car saveCar(CarForm car) {
        return carRepository.save(
                Car.builder()
                        .brand(car.getBrand())
                        .model(car.getModel())
                        .withDriver(car.getWithDriver())
                        .user(car.getUser())
                .build()
        );
    }
}
