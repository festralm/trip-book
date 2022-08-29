package ru.itis.tripbook.dto.user;

import lombok.Data;

@Data
public class UserEditForm {
    private String name;
    private String description;
    private String photoUrl;
}
