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
    private Timestamp start;
    private Timestamp finish;
    private List<String> carPhotoUrls;


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
                .start(book.getStart())
                .finish(book.getFinish())
                .carPhotoUrls(
                        car
                                .getCarPhotoUrls()
                                .stream()
                                .map(CarPhotoUrl::getUrl)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static List<CarBookForUserDto> from(List<Book> books) {
        return books == null ? new ArrayList<>() : books.stream()
                .filter(x -> !x.getCar().getIsDeleted()
                && !x.getCar().getIsBlocked() &&
                        !x.getUser().getIsBlocked() &&
                        !x.getUser().getIsDeleted())
                .map(CarBookForUserDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CarBookForUserDto{" +
                "id=" + id +
                ", withDriver=" + withDriver +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", forHour=" + forHour +
                ", start=" + start +
                ", finish=" + finish +
                ", " + carPhotoUrls.size() + " carPhotoUrls" +
                '}';
    }
}
