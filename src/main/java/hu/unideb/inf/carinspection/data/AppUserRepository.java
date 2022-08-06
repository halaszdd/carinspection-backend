package hu.unideb.inf.carinspection.data;

import hu.unideb.inf.carinspection.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);
    boolean existsByEmail(String email);
}
