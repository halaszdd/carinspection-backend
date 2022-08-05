package hu.unideb.inf.carinspection.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;
}
