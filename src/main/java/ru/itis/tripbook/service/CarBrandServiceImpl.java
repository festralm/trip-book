package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.controller.CarController;
import ru.itis.tripbook.dto.CarBrandDto;
import ru.itis.tripbook.exception.CarBrandNotFoundException;
import ru.itis.tripbook.model.CarBrand;
import ru.itis.tripbook.repository.CarBrandRepository;

import java.util.List;

@Service
public class CarBrandServiceImpl implements CarBrandService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CarBrandService.class);

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Override
    public List<CarBrandDto> getAllBrands() {
        var brands = carBrandRepository.findAll();
        var brandsDto = CarBrandDto.from(brands);
        LOGGER.info("Returning all {} brands", brandsDto.size());
        return brandsDto;
    }

    @Override
    public CarBrand getBrandById(Long brandId) throws CarBrandNotFoundException {
        LOGGER.info("Looking for brand with id {}", brandId);
        var brand = carBrandRepository.findById(brandId)
                .orElseThrow(() -> new CarBrandNotFoundException(brandId));
        LOGGER.info("Returning brand");
        return brand;
    }
}
