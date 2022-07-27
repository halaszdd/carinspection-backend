package hu.unideb.inf.carinspection.data;

import hu.unideb.inf.carinspection.domain.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
}
