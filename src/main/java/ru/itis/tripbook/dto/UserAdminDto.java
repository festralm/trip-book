package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class UserAdminDto {
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

    public static UserAdminDto from(User user) {
        return UserAdminDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .isBlocked(user.getIsBlocked())
                .isDeleted(user.getIsDeleted())
                .role(user.getRole())
                .cars(CarForUserDto.from(user.getCars()))
                .wishlist(CarForUserDto.from(user.getWishedCars()))
                .books(CarBookForUserDto.from(user.getBooks()))
                .name(user.getName())
                .joined(user.getJoined())
                .description(user.getDescription())
                .build();
    }

    public static List<UserAdminDto> from(List<User> users) {
        return users == null ? new ArrayList<>() : users.stream()
                .map(UserAdminDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "UserAdminDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", isBlocked=" + isBlocked +
                ", isDeleted=" + isDeleted +
                ", role=" + role +
                ", " + cars.size() + " cars" +
                ", " + wishlist.size() + " wishlist" +
                ", books=" + books +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", joined=" + joined +
                '}';
    }
}
