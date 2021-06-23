package ru.itis.tripbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.Loggable;
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
    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    @Loggable
    public List<CarModelDto> getModelsByBrandId(Long id) {
        var models = carModelRepository.getAllByBrandId(id);
        return CarModelDto.from(models);
    }

    @Override
    @Loggable
    public CarModel getModelById(Long modelId) throws CarModelNotFoundException {
        return carModelRepository.findById(modelId)
                .orElseThrow(() -> new CarModelNotFoundException(modelId));
    }
}
