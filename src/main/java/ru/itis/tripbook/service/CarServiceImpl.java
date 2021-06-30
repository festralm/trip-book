package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.annotation.SignatureLoggable;
import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.dto.car.CarForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.CarPhotoUrl;
import ru.itis.tripbook.repository.CarRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private CarModelService carModelService;

    @Override
    @ResultLoggable
    public CarDto saveCar(CarForm carForm)
            throws CarBrandNotFoundException, CarModelNotFoundException {
        if (carForm.getCarPhotoUrls() == null || carForm.getCarPhotoUrls().size() == 0) {
            carForm.setCarPhotoUrls( Collections.singletonList("default-car.png"));
        }

        var photosList = new ArrayList<CarPhotoUrl>();
        for (var url : carForm.getCarPhotoUrls()) {
            photosList.add(
                    CarPhotoUrl.builder()
                            .url(url)
                            .build()
            );
        }

        var car = Car.builder()
                .description(carForm.getDescription())
                .finish(carForm.getFinish())
                .forHour(carForm.getForHour())
                .isBlocked(false)
                .isDeleted(false)
                .name(carForm.getName())
                .price(carForm.getPrice())
                .start(carForm.getStart())
                .withDriver(carForm.getWithDriver())
                .brand(carBrandService.getBrandById(carForm.getBrand()))
                .model(carModelService.getModelById(carForm.getModel()))
                .user(carForm.getUser())
                .carPhotoUrls(photosList)
                .build();
        for (var url : photosList) {
            url.setCar(car);
        }
        var newCar = carRepository.save(car);
        return CarDto.from(newCar, false);
    }

    @Override
    @Loggable
    public List<CarDto> getBestCars(Long count) {
        var cars = carRepository.getBestOfCount(count);
        return CarDto.from(cars, false);
    }

    @Override
    @Loggable
    public CarDto getCarById(Long id)
            throws
            TransportNotFoundException,
            TransportIsBlockedException,
            TransportIsDeletedException {
        var car = getCarByIdAllDetails(id);
        var carDto = CarDto.from(car, false);
        if (car.getIsBlocked()) {
            throw new TransportIsBlockedException(id);
        }
        if (car.getIsDeleted()) {
            throw new TransportIsDeletedException(id);
        }
        return carDto;
    }

    @Override
    @SignatureLoggable
    public Car getCarByIdAllDetails(Long id) throws TransportNotFoundException {
        return carRepository.findById(id)
                .orElseThrow(() -> new TransportNotFoundException(id));
    }

    @SignatureLoggable
    @Override
    public CarDto deleteCarById(Long id) throws TransportNotFoundException, TransportIsDeletedException {
        var car = getCarByIdAllDetails(id);
        if (car.getIsDeleted()) {
            throw new TransportIsDeletedException(car.getId());
        }
        car.setIsDeleted(true);
        carRepository.save(car);
        return CarDto.from(car, true);
    }
    @SignatureLoggable
    @Override
    public CarDto banCarById(Long id) throws TransportNotFoundException, TransportIsBlockedException {
        var car = getCarByIdAllDetails(id);
        if (car.getIsBlocked()) {
            throw new TransportIsBlockedException(car.getId());
        }
        car.setIsBlocked(true);
        carRepository.save(car);
        return CarDto.from(car, true);
    }

    @SignatureLoggable
    @Override
    public CarDto restoreCarById(Long id) throws
            TransportNotFoundException,
            TransportIsNotDeletedException {
        var car = getCarByIdAllDetails(id);
        if (!car.getIsDeleted()) {
            throw new TransportIsNotDeletedException(car.getId());
        }
        car.setIsDeleted(false);
        carRepository.save(car);
        return CarDto.from(car, true);
    }
    @SignatureLoggable
    @Override
    public CarDto unbanCarById(Long id) throws
            TransportNotFoundException, TransportIsNotBlockedException {
        var car = getCarByIdAllDetails(id);
        if (!car.getIsBlocked()) {
            throw new TransportIsNotBlockedException(car.getId());
        }
        car.setIsBlocked(false);
        carRepository.save(car);
        return CarDto.from(car, true);
    }

}
