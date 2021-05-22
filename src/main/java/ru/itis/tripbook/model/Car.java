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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private Boolean withDriver;

    @Column
    private String name;

    @Column
    private Long price;

    @Column
    private Boolean forHour;

    @Column
    private Boolean isDeleted;

    @Column
    private Boolean isBlocked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column
    private String photo_url;
}
