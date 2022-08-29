package ru.itis.tripbook.service;

import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.exception.CarBrandNotFoundException;
import ru.itis.tripbook.exception.CarModelNotFoundException;
import ru.itis.tripbook.exception.UserNotFoundException;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.User;

import java.util.List;

@Service
public interface CarService {

    CarDto saveCar(CarForm car) throws CarBrandNotFoundException, CarModelNotFoundException;
}
