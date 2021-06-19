package ru.itis.tripbook.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.enums.Role;
import ru.itis.tripbook.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAdminSearchForm {
    private Long id;
    private String email;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Role role;

    public static UserAdminSearchForm from(User user) {
        return UserAdminSearchForm.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isBlocked(user.getIsBlocked())
                .isDeleted(user.getIsDeleted())
                .role(user.getRole())
                .build();
    }

    public static List<UserAdminSearchForm> from(List<User> users) {
        return users.stream()
                .map(UserAdminSearchForm::from)
                .collect(Collectors.toList());
    }
}
