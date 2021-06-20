package ru.itis.tripbook.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.CarBrand;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarBrandDto {
    private Long id;
    private String name;

    public static CarBrandDto from(CarBrand carBrand) {
        return CarBrandDto.builder()
                .id(carBrand.getId())
                .name(carBrand.getName())
                .build();
    }

    public static List<CarBrandDto> from(List<CarBrand> carBrands) {
        return carBrands.stream()
                .map(CarBrandDto::from)
                .collect(Collectors.toList());
    }
}
