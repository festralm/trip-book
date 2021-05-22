package ru.itis.tripbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.enums.*;

import javax.persistence.*;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private Boolean withDriver;

    @Column
    private String brand;

    @Column
    private String model;

//    @Enumerated(value = EnumType.STRING)
//    private BodyType bodyType;
//
//    @Enumerated(value = EnumType.STRING)
//    private TransmissionType transmissionType;
//
//    @Enumerated(value = EnumType.STRING)
//    private EngineType engineType;
//
//
//    @Enumerated(value = EnumType.STRING)
//    private DriveUnit driveUnit;
//
//    @Column
//    private Boolean isRightWheel;
//
//    @Column
//    private Integer maxSeatsCount;
//
//    @Column
//    private Integer engineVolume;
//
//    @Column
//    private Integer enginePower;
//
//    @Column
//    private Integer acceleration;
//
//    @Column
//    private Integer year;
//
//    @Column
//    private Long price;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User owner;


}
