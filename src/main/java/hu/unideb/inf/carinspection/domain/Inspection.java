package hu.unideb.inf.carinspection.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inspection {
    @Id
    public long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Car car;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Inspector inspector;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Site site;
    public LocalDate date;
    public String result;
    public String comment;
}
