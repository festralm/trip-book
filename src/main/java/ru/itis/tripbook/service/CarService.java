package ru.itis.tripbook.service;

import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.admin.CarAdminForm;
import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.dto.car.CarEditForm;
import ru.itis.tripbook.dto.car.CarForm;
import ru.itis.tripbook.dto.car.CarSearchForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Car;

import java.util.List;

@Service
public interface CarService {

    CarDto saveCar(CarForm car) throws CarBrandNotFoundException, CarModelNotFoundException;

    List<CarDto> getBestCars(Long count);

    CarDto getCarById(Long id) throws CarNotFoundException, CarIsBlockedException, CarIsDeletedException;

    Car getCarByIdAllDetails(Long id) throws CarNotFoundException;

    CarDto deleteCarById(Long id) throws CarNotFoundException, CarIsDeletedException;

    CarDto restoreCarById(Long id) throws CarNotFoundException, CarIsNotDeletedException;

    CarDto banCarById(Long id) throws CarNotFoundException, CarIsBlockedException;

    CarDto unbanCarById(Long id) throws CarNotFoundException, CarIsNotBlockedException;

    CarDto editCar(Long id, CarEditForm carForm) throws CarNotFoundException;

    CarDto getCarByIdForAdmin(Long id) throws CarNotFoundException;

    List<CarDto> findCarsForAdmin(CarAdminForm car) throws CarBrandNotFoundException, CarModelNotFoundException;

    List<CarDto> findCars(CarSearchForm car) throws CarBrandNotFoundException, CarModelNotFoundException;
}
