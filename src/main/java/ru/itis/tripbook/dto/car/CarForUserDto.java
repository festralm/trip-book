package ru.itis.tripbook.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.dto.review.ReviewForCarDto;
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
public class CarForUserDto {
    private Long id;
    private Boolean withDriver;
    private String brand;
    private String model;
    private String name;
    private Timestamp start;
    private Timestamp finish;
    private Boolean isDeleted;
    private Boolean isBlocked;
    private List<String> carPhotoUrls;
    private String rating;
    private List<ReviewForCarDto> reviews;


    public static CarForUserDto from(Car car, boolean allDetails) {
        var carDto = CarForUserDto.builder()
                .id(car.getId())
                .withDriver(car.getWithDriver())
                .brand(car.getBrand().getName())
                .model(car.getModel().getName())
                .name(car.getName())
                .start(car.getStart())
                .finish(car.getFinish())
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
                    carDto.getReviews()) {
                rating += review.getRating();
            }
            rating /= carDto.getReviews().size();
        }
        carDto.setRating(String.format(Locale.US, "%.2f", rating));
        if (allDetails) {
            carDto.setIsDeleted(car.getIsDeleted());
            carDto.setIsBlocked(car.getIsBlocked());
        }
        return carDto;
    }

    public static List<CarForUserDto> from(List<Car> cars, boolean allDetails) {
        return cars == null ? new ArrayList<>() : cars.stream()
                .filter(x ->
                        allDetails || !x.getIsBlocked() && !x.getIsDeleted())
                .map(x -> CarForUserDto.from(x, allDetails))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CarForUserDto{" +
                "id=" + id +
                ", withDriver=" + withDriver +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                ", " + carPhotoUrls.size() + " carPhotoUrls" +
                ", rating='" + rating + '\'' +
                '}';
    }
}
