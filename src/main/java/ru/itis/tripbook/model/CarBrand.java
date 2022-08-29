package ru.itis.tripbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarBrand {
    @Id
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "brand"  )
    private List<Car> cars;

    @OneToMany(mappedBy = "brand" )
    private List<CarModel> models;
}
