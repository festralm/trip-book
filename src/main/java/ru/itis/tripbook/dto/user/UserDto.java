package ru.itis.tripbook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.dto.car.CarBookForUserDto;
import ru.itis.tripbook.dto.car.CarForUserDto;
import ru.itis.tripbook.dto.review.ReviewForUserDto;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.model.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String photoUrl;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Role role;
    private List<CarForUserDto> cars;
    private List<CarForUserDto> wishlist;
    private List<CarBookForUserDto> books;
    private String name;
    private String description;
    private Timestamp joined;
    private List<ReviewForUserDto> reviews;

    public static UserDto from(User user, boolean allDetails) {
        var userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .role(user.getRole())
                .cars(CarForUserDto.from(user.getCars(), allDetails))
                .wishlist(CarForUserDto.from(user.getWishedCars(), allDetails))
                .books(CarBookForUserDto.from(user.getBooks(), allDetails))
                .name(user.getName())
                .joined(user.getJoined())
                .description(user.getDescription())
                .reviews(ReviewForUserDto.from(user.getReviews(), allDetails))
                .build();
        if (allDetails) {
            userDto.setIsDeleted(user.getIsDeleted());
            userDto.setIsBlocked(user.getIsBlocked());
        }
        return userDto;
    }

    public static List<UserDto> from(List<User> users, boolean allDetails) {
        return users == null ? new ArrayList<>() : users.stream()
                .filter(x ->
                        !x.getIsBlocked() &&
                                !x.getIsDeleted())
                .map(x -> UserDto.from(x, allDetails))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", role=" + role +
                ", " + cars.size() + " cars" +
                ", " + wishlist.size() + " wishlist" +
                ", " + books.size() + " books" +
                ", name='" + name + '\'' +
                ", joined=" + joined +
                ", description='" + description + '\'' +
                '}';
    }
}
