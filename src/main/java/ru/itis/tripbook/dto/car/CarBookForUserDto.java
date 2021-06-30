package ru.itis.tripbook.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.dto.review.ReviewForCarDto;
import ru.itis.tripbook.model.Book;
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
    private Boolean isDeleted;
    private Boolean isBlocked;
    private List<ReviewForCarDto> reviews;
    private String rating;


    public static CarBookForUserDto from(Book book, boolean allDetails) {
        var car = book.getCar();
        var carDto = CarBookForUserDto.builder()
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
                .reviews(ReviewForCarDto.from(car.getReviews(), allDetails))
                .build();

        var rating = 0.0;
        if (carDto.getReviews().size() != 0) {
            for (var review :
                    car.getReviews()) {
                rating += review.getRating();
            }
            rating = rating / car.getReviews().size();
        }
        carDto.setRating(String.format(Locale.US, "%.2f", rating));
        if (allDetails) {
            carDto.setIsDeleted(car.getIsDeleted());
            carDto.setIsBlocked(car.getIsBlocked());
        }
        return carDto;
    }

    public static List<CarBookForUserDto> from(List<Book> books, boolean allDetails) {
        return books == null ? new ArrayList<>() : books.stream()
                .filter(x -> allDetails ||
                        !x.getCar().getIsDeleted()
                && !x.getCar().getIsBlocked() &&
                        !x.getUser().getIsBlocked() &&
                        !x.getUser().getIsDeleted())
                .map(x -> CarBookForUserDto.from(x, allDetails))
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
