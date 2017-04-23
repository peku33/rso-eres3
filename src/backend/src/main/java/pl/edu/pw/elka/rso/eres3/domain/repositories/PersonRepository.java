package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Person getPersonByLogin(String username);
}
