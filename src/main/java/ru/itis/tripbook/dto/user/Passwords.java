package ru.itis.tripbook.dto.user;

import lombok.Data;

@Data
public class Passwords {
    private String oldPassword;
    private String newPassword;
}
