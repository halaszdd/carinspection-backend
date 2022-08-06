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
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String groupName;
    @OneToMany(mappedBy = "group", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<AppUser> appUser;
}
