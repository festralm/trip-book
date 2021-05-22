package ru.itis.tripbook.service;

import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.User;

@Service
public interface CarService {

    Car saveCar(CarForm car);
}
