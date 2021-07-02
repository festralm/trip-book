package ru.itis.tripbook.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.dto.book.BookDto;
import ru.itis.tripbook.dto.review.ReviewForCarDto;
import ru.itis.tripbook.dto.user.UserForCarDto;
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
    private Boolean isDeleted;
    private Boolean isBlocked;
    private UserForCarDto user;
    private String rating;
    private List<BookDto> books;
    private List<ReviewForCarDto> reviews;
    private Double lat;
    private Double lng;


    public static CarDto from(Car car, boolean allDetails) {
        var carDto = CarDto.builder()
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
                .books(BookDto.from(car.getBooks()))
                .reviews(ReviewForCarDto.from(car.getReviews(), allDetails))
                .lat(car.getLat())
                .lng(car.getLng())
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

    public static List<CarDto> from(List<Car> cars, boolean allDetails) {
        return cars == null ? new ArrayList<>() : cars.stream()
                .filter(x -> allDetails ||
                        !x.getIsBlocked() &&
                        !x.getIsDeleted())
                .map(x -> CarDto.from(x, allDetails))
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
