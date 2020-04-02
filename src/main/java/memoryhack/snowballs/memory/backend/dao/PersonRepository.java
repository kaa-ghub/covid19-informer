package memoryhack.snowballs.memory.backend.dao;

import memoryhack.snowballs.memory.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
