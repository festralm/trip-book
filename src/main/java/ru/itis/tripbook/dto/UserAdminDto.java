package ru.itis.tripbook.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel
public class UserAdminDto {
    private String email;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Role role;

    public static UserAdminDto from(User user) {
        return UserAdminDto.builder()
                .email(user.getEmail())
                .isBlocked(user.getIsBlocked())
                .isDeleted(user.getIsDeleted())
                .role(user.getRole())
                .build();
    }

    public static List<UserAdminDto> from(List<User> users) {
        return users.stream()
                .map(UserAdminDto::from)
                .collect(Collectors.toList());
    }
}
