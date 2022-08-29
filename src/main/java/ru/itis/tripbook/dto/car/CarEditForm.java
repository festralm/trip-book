package ru.itis.tripbook.dto.car;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CarEditForm {
    private String name;
    private List<String> carPhotoUrls;
    private Boolean withDriver;
    private String description;
    private Timestamp start;
    private Timestamp finish;
    private Long price;
    private Boolean forHour;
    private Double lat;
    private Double lng;
}
