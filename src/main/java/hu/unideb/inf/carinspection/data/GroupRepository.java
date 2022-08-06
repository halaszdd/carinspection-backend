package hu.unideb.inf.carinspection.data;

import hu.unideb.inf.carinspection.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByGroupName(String groupName);
}
