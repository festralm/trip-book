package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.dto.car.CarBrandDto;
import ru.itis.tripbook.exception.CarBrandNotFoundException;
import ru.itis.tripbook.model.CarBrand;
import ru.itis.tripbook.repository.CarBrandRepository;

import java.util.List;

@Service
public class CarBrandServiceImpl implements CarBrandService {
    @Autowired
    private CarBrandRepository carBrandRepository;

    @Loggable
    @Override
    public List<CarBrandDto> getAllBrands() {
        var brands = carBrandRepository.findAll();
        return CarBrandDto.from(brands);
    }

    @Loggable
    @Override
    public CarBrand getBrandById(Long brandId) throws CarBrandNotFoundException {
        return carBrandRepository.findById(brandId)
                .orElseThrow(() -> new CarBrandNotFoundException(brandId));
    }
}
