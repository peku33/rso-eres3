package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;

/**
 * Created by Jakub Lorenc on 21.04.17.
 */
public interface PersonRepository extends CrudRepository<Person, String> {
}
