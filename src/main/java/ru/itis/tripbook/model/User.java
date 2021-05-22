package ru.itis.tripbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tripbook.model.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String hashPassword;

    @Column
    private Boolean isDeleted;

    @Column
    private Boolean isBlocked;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER   )
    private List<Car> transports;

}
