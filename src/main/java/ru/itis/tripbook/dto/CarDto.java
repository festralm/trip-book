package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto {
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
    private UserForCarDto user;
    private String rating;
    private List<BookDto> books;


    public static CarDto from(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .withDriver(car.getWithDriver())
                .brand(car.getBrand().getName())
                .model(car.getModel().getName())
                .name(car.getName())
                .price(car.getPrice())
                .forHour(car.getForHour())
                .description(car.getDescription())
                .start(car.getStart())
                .finish(car.getFinish())
                .carPhotoUrls(

                        car
                                .getCarPhotoUrls()
                                .stream()
                                .map(CarPhotoUrl::getUrl)
                                .collect(Collectors.toList())
                )
                .user(UserForCarDto.from(car.getUser()))
                .rating(String.format(Locale.US, "%.2f", car.getRating()))
                .books(BookDto.from(car.getBooks()))
                .build();
    }

    public static List<CarDto> from(List<Car> cars) {
        return cars == null ? new ArrayList<>() : cars.stream()
                .map(CarDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "id=" + id +
                ", withDriver=" + withDriver +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", forHour=" + forHour +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                ", " + carPhotoUrls.size() + " carPhotoUrls" +
                ", user=" + user +
                ", rating='" + rating + '\'' +
                ", " + books.size() + " books" +
                '}';
    }
}