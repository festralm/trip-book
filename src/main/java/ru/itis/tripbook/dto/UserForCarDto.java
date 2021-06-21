package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForCarDto {
    private Long id;
    private String email;
    private String photoUrl;
    private String name;
    private Timestamp joined;
    private String description;

    public static UserForCarDto from(User user) {
        return UserForCarDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .name(user.getName())
                .joined(user.getJoined())
                .description(user.getDescription())
                .build();
    }

    public static List<UserForCarDto> from(List<User> users) {
        return users == null ? new ArrayList<>() : users.stream()
                .map(UserForCarDto::from)
                .collect(Collectors.toList());
    }
}
