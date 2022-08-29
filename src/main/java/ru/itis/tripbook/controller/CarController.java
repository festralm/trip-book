package ru.itis.tripbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.tripbook.dto.CarDto;
import ru.itis.tripbook.dto.CarForm;
import ru.itis.tripbook.exception.CarBrandNotFoundException;
import ru.itis.tripbook.exception.CarModelNotFoundException;
import ru.itis.tripbook.model.CarPhotoUrl;
import ru.itis.tripbook.security.UserDetailsImpl;
import ru.itis.tripbook.service.CarBrandService;
import ru.itis.tripbook.service.CarModelService;
import ru.itis.tripbook.service.CarService;

import java.util.Collections;

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
}
