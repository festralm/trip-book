package ru.itis.tripbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Boolean withDriver;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel model;

    @Column
    private String name;

    @Column
    private Long price;

    @Column
    private Boolean forHour;

    @Column(length = 1000)
    private String description;

    @Column
    private Timestamp start;

    @Column
    private Timestamp finish;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<CarPhotoUrl> carPhotoUrls;

    @Column
    private Boolean isDeleted;

    @Column
    private Boolean isBlocked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "car" )
    private List<Book> books;

    @OneToMany(mappedBy = "car")
    private List<Review> reviews;

    @Column
    private Double lat;

    @Column
    private Double lng;
}
