package hu.unideb.inf.carinspection.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    public Site site;
    public String firstName;
    public String lastName;
}
