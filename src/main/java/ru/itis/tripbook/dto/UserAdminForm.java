package ru.itis.tripbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.User;
import ru.itis.tripbook.model.Role;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAdminForm {
    private Long id;
    private String email;
    private String photoUrl;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Role role;

    public static UserAdminForm from(User user) {
        return UserAdminForm.builder()
                .id(user.getId())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .isBlocked(user.getIsBlocked())
                .isDeleted(user.getIsDeleted())
                .role(user.getRole())
                .build();
    }

    public static List<UserAdminForm> from(List<User> users) {
        return users.stream()
                .map(UserAdminForm::from)
                .collect(Collectors.toList());
    }
}
