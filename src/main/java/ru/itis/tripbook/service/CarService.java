package ru.itis.tripbook.service;

import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.BookDto;
import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Car;

import java.util.List;

@Service
public interface CarService {

    CarDto saveCar(CarForm car) throws CarBrandNotFoundException, CarModelNotFoundException;

    List<CarDto> getBestCars(Long count);

    CarDto getCarById(Long id) throws TransportNotFoundException, TransportIsBlockedException, TransportIsDeletedException;

    Car getCarByIdAllDetails(Long id) throws TransportNotFoundException;
}
