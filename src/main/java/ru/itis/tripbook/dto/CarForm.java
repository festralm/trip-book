package ru.itis.tripbook.dto;

import lombok.Data;
import ru.itis.tripbook.model.User;

import javax.persistence.Column;

@Data
public class CarForm {
    private Boolean withDriver;
    private String brand;
    private String model;
    private User user;
    private String name;
    private Long price;
    private Boolean forHour;
    private String photo_url;
}
