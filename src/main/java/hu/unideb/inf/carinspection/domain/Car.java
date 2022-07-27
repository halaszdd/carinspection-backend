package hu.unideb.inf.carinspection.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public AppUser owner;
    public String plateNumber;
    public String vin;
    public LocalDate expirationDate;
}
