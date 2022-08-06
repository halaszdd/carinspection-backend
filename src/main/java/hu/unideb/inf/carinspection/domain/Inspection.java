package hu.unideb.inf.carinspection.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Inspection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    public Car car;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    public Inspector inspector;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    public Site site;
    public LocalDate date;
    public String result;
    public String comment;
}
