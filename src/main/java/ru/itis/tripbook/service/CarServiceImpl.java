package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.annotation.SignatureLoggable;
import ru.itis.tripbook.dto.*;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.CarPhotoUrl;
import ru.itis.tripbook.repository.CarRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return CarDto.from(newCar);
    }

    @Override
    @Loggable
    public List<CarDto> getBestCars(Long count) {
        var cars = carRepository.getBestOfCount(count);
        return CarDto.from(cars);
    }

    @Override
    @Loggable
    public CarDto getCarById(Long id)
            throws
            TransportNotFoundException,
            TransportIsBlockedException,
            TransportIsDeletedException {
        var car = getCarByIdAllDetails(id);
        var carDto = CarDto.from(car);
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

}
