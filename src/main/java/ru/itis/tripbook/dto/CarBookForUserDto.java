package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Book;
import ru.itis.tripbook.model.Car;
import ru.itis.tripbook.model.CarPhotoUrl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarBookForUserDto {
    private Long id;
    private Boolean withDriver;
    private String brand;
    private String model;
    private String name;
    private Long price;
    private Boolean forHour;
    private String description;
    private Timestamp start;
    private Timestamp finish;
    private List<String> carPhotoUrls;
    private String rating;


    public static CarBookForUserDto from(Book book) {
        var car = book.getCar();
        return CarBookForUserDto.builder()
                .id(car.getId())
                .withDriver(car.getWithDriver())
                .brand(car.getBrand().getName())
                .model(car.getModel().getName())
                .name(car.getName())
                .price(car.getPrice())
                .forHour(car.getForHour())
                .description(car.getDescription())
                .start(book.getStart())
                .finish(book.getFinish())
                .carPhotoUrls(
                        car
                                .getCarPhotoUrls()
                                .stream()
                                .map(CarPhotoUrl::getUrl)
                                .collect(Collectors.toList())
                )
                .rating(String.format(Locale.US, "%.2f", car.getRating()))
                .build();
    }

    public static List<CarBookForUserDto> from(List<Book> books) {
        return books == null ? new ArrayList<>() : books.stream()
                .map(CarBookForUserDto::from)
                .collect(Collectors.toList());
    }
}
