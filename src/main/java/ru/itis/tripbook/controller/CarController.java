package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.dto.UserDto;
import ru.itis.tripbook.exception.*;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.*;

@RestController
@RequestMapping("/car")
public class CarController {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> authenticate(@RequestBody CarForm car,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        LOGGER.info("Got CarForm {}", car);
        car.setUser(user.getUser());
        LOGGER.info("Set user to car");
        CarDto carDto = null;

        try {
            carDto = carService.saveCar(car);
        } catch (CarBrandNotFoundException e) {
            LOGGER.error("Brand is not found");
            var myBody = new MyResponseBody(MyStatus.BRAND_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (CarModelNotFoundException e) {
            LOGGER.error("Model is not found");
            var myBody = new MyResponseBody(MyStatus.MODEL_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
        LOGGER.info("Returning saved carDto {}", carDto);
        return ResponseEntity.ok().body(carDto);
    }

    @GetMapping("/brands")
    public ResponseEntity<?> getCarBrands() {
        LOGGER.info("Getting car brands");
        var brands = carBrandService.getAllBrands();
        LOGGER.info("Returning status 200(OK) and brands List of CarBrandDto: {}", brands.toString());
        return ResponseEntity.ok().body(brands);
    }

    @GetMapping("/{id}/models")
    public ResponseEntity<?> getCarModelsByBrandId(@PathVariable Long id) {
        LOGGER.info("Getting car models by id {}", id);
        var brands = carModelService.getModelsByBrandId(id);
        LOGGER.info("Returning status 200(OK) and models List of CarModelDto: {}", brands.toString());
        return ResponseEntity.ok().body(brands);
    }

    @GetMapping("/best/{count}")
    public ResponseEntity<?> getBestCarsOfCount(@PathVariable Long count) {
        LOGGER.info("Getting {} best cars}", count);
        var cars = carService.getBestCars(count);
        LOGGER.info("Returning status 200(OK) and List of {} CarDto", cars.size());
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id) {
        LOGGER.info("Getting car with id {}", id);
        CarDto car = null;
        try {
            car = carService.getCarById(id);
        } catch (TransportNotFoundException e) {
            LOGGER.info("Car is not found");
            var myBody = new MyResponseBody(MyStatus.TRANSPORT_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (TransportIsBlockedException e) {
            LOGGER.info("Car is blocked");
            var myBody = new MyResponseBody(MyStatus.TRANSPORT_IS_BLOCKED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (TransportIsDeletedException e) {
            LOGGER.info("Car is deleted");
            var myBody = new MyResponseBody(MyStatus.TRANSPORT_IS_DELETED);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
        LOGGER.info("Returning status 200(OK) and CarDto");
        return ResponseEntity.ok().body(car);
    }


    @PostMapping("/wishlist/{id}")
    public ResponseEntity<?> addToWishList(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        LOGGER.info("Adding car with id {} to wishlist", id);
        UserDto userDto = null;
        try {
            userDto = userService.addToWishlist(id, user.getUser().getId());
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (TransportNotFoundException e) {
            LOGGER.info("Car is not found");
            var myBody = new MyResponseBody(MyStatus.TRANSPORT_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
        LOGGER.info("Returning status 200(OK) and userDto {}", userDto.toString());
        return ResponseEntity.ok().body(userDto);
    }
    @PostMapping("/delete-wishlist/{id}")
    public ResponseEntity<?> deleteFromWishList(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        LOGGER.info("Removing car with id {} from wishlist", id);
        UserDto userDto = null;
        try {
            userDto = userService.deleteFromWishlist(id, user.getUser().getId());
        } catch (UserNotFoundException e) {
            LOGGER.info("User is not found");
            var myBody = new MyResponseBody(MyStatus.USER_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        } catch (TransportNotFoundException e) {
            LOGGER.info("Car is not found");
            var myBody = new MyResponseBody(MyStatus.TRANSPORT_IS_NOT_FOUND);
            LOGGER.info("Returning {}", myBody);
            return ResponseEntity.ok().body(myBody);
        }
        LOGGER.info("Returning status 200(OK) and userDto {}", userDto.toString());
        return ResponseEntity.ok().body(userDto);
    }
}
