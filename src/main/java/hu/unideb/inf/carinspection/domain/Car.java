package hu.unideb.inf.carinspection.domain;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private AppUser owner;
    private String plateNumber;
    private String vin;
    private LocalDate expirationDate;
    @OneToMany(mappedBy = "car", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Inspection> inspections;
}
