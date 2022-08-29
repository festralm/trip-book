package ru.itis.tripbook.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarSearchForm {
    private Timestamp start;
    private Timestamp finish;
    private Boolean withDriver;
    private Long brand;
    private Long model;
}
