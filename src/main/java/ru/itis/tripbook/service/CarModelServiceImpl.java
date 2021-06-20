package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.dto.CarBrandDto;
import ru.itis.tripbook.dto.CarModelDto;
import ru.itis.tripbook.exception.CarBrandNotFoundException;
import ru.itis.tripbook.exception.CarModelNotFoundException;
import ru.itis.tripbook.model.CarModel;
import ru.itis.tripbook.repository.CarBrandRepository;
import ru.itis.tripbook.repository.CarModelRepository;

import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CarModelService.class);

    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    public List<CarModelDto> getModelsByBrandId(Long id) {
        var models = carModelRepository.getAllByBrandId(id);
        var modelsDto = CarModelDto.from(models);
        LOGGER.info("Returning all {} models by brand id {}", modelsDto.size(), id);
        return modelsDto;
    }

    @Override
    public CarModel getModelById(Long modelId) throws CarModelNotFoundException {
        LOGGER.info("Looking for model with id {}", modelId);
        var model = carModelRepository.findById(modelId)
                .orElseThrow(() -> new CarModelNotFoundException(modelId));
        LOGGER.info("Returning model");
        return model;
    }
}
