package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Book;
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
    private Role role;
    private List<CarForUserDto> cars;
    private List<CarForUserDto> wishlist;
    private List<CarBookForUserDto> books;
    private String name;
    private Timestamp joined;
    private String description;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .role(user.getRole())
                .cars(CarForUserDto.from(user.getCars()))
                .wishlist(CarForUserDto.from(user.getWishedCars()))
                .books(CarBookForUserDto.from(user.getBooks()))
                .name(user.getName())
                .joined(user.getJoined())
                .description(user.getDescription())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users == null ? new ArrayList<>() : users.stream()
                .map(UserDto::from)
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
