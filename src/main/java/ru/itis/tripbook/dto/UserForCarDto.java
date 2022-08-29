package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Role;
import ru.itis.tripbook.model.User;

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

    public static UserForCarDto from(User user) {
        return UserForCarDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .build();
    }

    public static List<UserForCarDto> from(List<User> users) {
        return users.stream()
                .map(UserForCarDto::from)
                .collect(Collectors.toList());
    }
}
