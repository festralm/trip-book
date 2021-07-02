package ru.itis.tripbook.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.Role;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarAdminForm {
    private Long id;
    private String name;
    private Long brand;
    private Long model;
    private Boolean isBlocked;
    private Boolean isDeleted;
}
