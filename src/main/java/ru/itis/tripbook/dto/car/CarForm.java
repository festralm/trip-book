package ru.itis.tripbook.dto.car;

import lombok.Data;
import ru.itis.tripbook.model.CarBrand;
import ru.itis.tripbook.model.CarModel;
import ru.itis.tripbook.model.CarPhotoUrl;
import ru.itis.tripbook.model.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Data
public class CarForm {
    private Boolean withDriver;
    private Long brand;
    private Long model;
    private String name;
    private Long price;
    private Boolean forHour;
    private String description;
    private Timestamp start;
    private Timestamp finish;
    private List<String> carPhotoUrls;
    private User user;
    private Double lat;
    private Double lng;
}
