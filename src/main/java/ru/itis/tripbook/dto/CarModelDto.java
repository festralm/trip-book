package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.CarBrand;
import ru.itis.tripbook.model.CarModel;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarModelDto {
    private Long id;
    private String name;

    public static CarModelDto from(CarModel carModel) {
        return CarModelDto.builder()
                .id(carModel.getId())
                .name(carModel.getName())
                .build();
    }

    public static List<CarModelDto> from(List<CarModel> carModels) {
        return carModels.stream()
                .map(CarModelDto::from)
                .collect(Collectors.toList());
    }
}
