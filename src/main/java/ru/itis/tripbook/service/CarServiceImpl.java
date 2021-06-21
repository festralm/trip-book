package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.BookDto;
import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.CarPhotoUrl;
import ru.itis.tripbook.repository.CarRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private CarModelService carModelService;

    @Override
    public CarDto saveCar(CarForm carForm) throws CarBrandNotFoundException, CarModelNotFoundException {

        if (carForm.getCarPhotoUrls() == null || carForm.getCarPhotoUrls().size() == 0) {
            carForm.setCarPhotoUrls( Collections.singletonList("default-car.png"));
            LOGGER.info("Setting default carPhoto");
        }

        var photosList = new ArrayList<CarPhotoUrl>();
        for (var url : carForm.getCarPhotoUrls()) {
            photosList.add(
                    CarPhotoUrl.builder()
                            .url(url)
                            .build()
            );
        }
        LOGGER.info("Mapped string urls to list of CarPhotoUrl");

        var car = Car.builder()
                .description(carForm.getDescription())
                .finish(carForm.getFinish())
                .forHour(carForm.getForHour())
                .isBlocked(false)
                .isDeleted(false)
                .name(carForm.getName())
                .price(carForm.getPrice())
                .rating(5.0)
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
        var carDto = CarDto.from(newCar);
        LOGGER.info("Saved new car, returning CarDto");
        return carDto;
    }

    @Override
    public List<CarDto> getBestCars(Long count) {
        var cars = carRepository.getBestOfCount(count);
        var carsDto = CarDto.from(cars);
        LOGGER.info("Got cars {}", carsDto);
        return carsDto;
    }

    @Override
    public CarDto getCarById(Long id)
            throws
            TransportNotFoundException,
            TransportIsBlockedException,
            TransportIsDeletedException {
        var car = getCarByIdAllDetails(id);
        var carDto = CarDto.from(car);
        LOGGER.info("Got car {}", carDto);
        if (car.getIsBlocked()) {
            LOGGER.info("Car is blocked");
            throw new TransportIsBlockedException(id);
        }
        if (car.getIsDeleted()) {
            LOGGER.info("Car is deleted");
            throw new TransportIsDeletedException(id);
        }
        LOGGER.info("Returning CarDto");
        return carDto;
    }

    @Override
    public Car getCarByIdAllDetails(Long id) throws TransportNotFoundException {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new TransportNotFoundException(id));
        LOGGER.info("Returning Car with all details");
        return car;
    }
}
