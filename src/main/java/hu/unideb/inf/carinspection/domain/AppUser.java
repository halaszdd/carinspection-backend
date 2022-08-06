package hu.unideb.inf.carinspection.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Car> cars;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Group group;
}
