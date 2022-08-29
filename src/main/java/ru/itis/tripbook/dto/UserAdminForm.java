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
public class UserAdminForm {
    private Long id;
    private String email;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Role role;
    private Timestamp joined;
    private String description;

    public static UserAdminForm from(User user) {
        return UserAdminForm.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isBlocked(user.getIsBlocked())
                .isDeleted(user.getIsDeleted())
                .role(user.getRole())
                .joined(user.getJoined())
                .description(user.getDescription())
                .build();
    }

    public static List<UserAdminForm> from(List<User> users) {
        return users == null ? new ArrayList<>() : users.stream()
                .map(UserAdminForm::from)
                .collect(Collectors.toList());
    }
}
