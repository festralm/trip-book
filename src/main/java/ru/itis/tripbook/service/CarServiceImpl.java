package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.annotation.ResultLoggable;
import ru.itis.tripbook.annotation.SignatureLoggable;
import ru.itis.tripbook.dto.admin.CarAdminForm;
import ru.itis.tripbook.dto.car.CarDto;
import ru.itis.tripbook.dto.car.CarEditForm;
import ru.itis.tripbook.dto.car.CarForm;
import ru.itis.tripbook.dto.car.CarSearchForm;
import ru.itis.tripbook.dto.user.UserDto;
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
                .lat(carForm.getLat())
                .lng(carForm.getLng())
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
            CarNotFoundException,
            CarIsBlockedException,
            CarIsDeletedException {
        var car = getCarByIdAllDetails(id);
        var carDto = CarDto.from(car, false);
        if (car.getIsBlocked()) {
            throw new CarIsBlockedException(id);
        }
        if (car.getIsDeleted()) {
            throw new CarIsDeletedException(id);
        }
        return carDto;
    }

    @Override
    @SignatureLoggable
    public Car getCarByIdAllDetails(Long id) throws CarNotFoundException {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @SignatureLoggable
    @Override
    public CarDto deleteCarById(Long id) throws CarNotFoundException, CarIsDeletedException {
        var car = getCarByIdAllDetails(id);
        if (car.getIsDeleted()) {
            throw new CarIsDeletedException(car.getId());
        }
        car.setIsDeleted(true);
        carRepository.save(car);
        return CarDto.from(car, true);
    }
    @SignatureLoggable
    @Override
    public CarDto banCarById(Long id) throws CarNotFoundException, CarIsBlockedException {
        var car = getCarByIdAllDetails(id);
        if (car.getIsBlocked()) {
            throw new CarIsBlockedException(car.getId());
        }
        car.setIsBlocked(true);
        carRepository.save(car);
        return CarDto.from(car, true);
    }

    @SignatureLoggable
    @Override
    public CarDto restoreCarById(Long id) throws
            CarNotFoundException,
            CarIsNotDeletedException {
        var car = getCarByIdAllDetails(id);
        if (!car.getIsDeleted()) {
            throw new CarIsNotDeletedException(car.getId());
        }
        car.setIsDeleted(false);
        carRepository.save(car);
        return CarDto.from(car, true);
    }
    @SignatureLoggable
    @Override
    public CarDto unbanCarById(Long id) throws
            CarNotFoundException, CarIsNotBlockedException {
        var car = getCarByIdAllDetails(id);
        if (!car.getIsBlocked()) {
            throw new CarIsNotBlockedException(car.getId());
        }
        car.setIsBlocked(false);
        carRepository.save(car);
        return CarDto.from(car, true);
    }

    @Override
    @Loggable
    public CarDto editCar(Long id, CarEditForm carForm) throws CarNotFoundException {
        var car = getCarByIdAllDetails(id);
        if (carForm.getName() != null) {
            car.setName(carForm.getName());
        }
        if (carForm.getCarPhotoUrls() != null) {
            for (int i = 0; i < car.getCarPhotoUrls().size(); i++) {
                car.getCarPhotoUrls().remove(car.getCarPhotoUrls().get(i));
            }
            for (var url : carForm.getCarPhotoUrls()) {
                car.getCarPhotoUrls().add(
                        CarPhotoUrl.builder()
                                .url(url)
                                .car(car)
                                .build()
                );
            }
        }
        if (carForm.getWithDriver() != null) {
            car.setWithDriver(carForm.getWithDriver());
        }
        if (carForm.getDescription() != null) {
            car.setDescription(carForm.getDescription());
        }
        if (carForm.getStart() != null) {
            car.setStart(carForm.getStart());
        }
        if (carForm.getFinish() != null) {
            car.setFinish(carForm.getFinish());
        }
        if (carForm.getPrice() != null) {
            car.setPrice(carForm.getPrice());
        }
        if (carForm.getForHour() != null) {
            car.setForHour(carForm.getForHour());
        }
        return CarDto.from(carRepository.save(car), false);
    }

    @Override
    @Loggable
    public CarDto getCarByIdForAdmin(Long id) throws CarNotFoundException {

        return CarDto.from(getCarByIdAllDetails(id), true);

    }

    @Override
    @Loggable
    public List<CarDto> findCarsForAdmin(CarAdminForm car) throws CarBrandNotFoundException, CarModelNotFoundException {

        return CarDto.from(carRepository.findCarsByParams(
                car.getId(),
                car.getName(),
                car.getBrand() == null ? null : carBrandService.getBrandById(car.getBrand()),
                car.getModel() == null ? null : carModelService.getModelById(car.getModel()),
                car.getIsDeleted(),
                car.getIsBlocked()
        ), true);
    }

    @Override
    @Loggable
    public List<CarDto> findCars(CarSearchForm car) throws CarBrandNotFoundException, CarModelNotFoundException {
        return CarDto.from(carRepository.findCars(
                car.getStart(),
                car.getFinish(),
                car.getWithDriver(),
                car.getBrand() == null ? null : carBrandService.getBrandById(car.getBrand()),
                car.getModel() == null ? null : carModelService.getModelById(car.getModel())
        ), false);
    }

}
