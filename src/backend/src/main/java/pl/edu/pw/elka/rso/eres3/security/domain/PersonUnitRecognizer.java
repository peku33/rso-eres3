package pl.edu.pw.elka.rso.eres3.security.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

/**
 * Unit recognizer for persons.
 */
@Component
public class PersonUnitRecognizer extends EntityUnitRecognizer<Person, Long>{

	@Autowired
	public PersonUnitRecognizer(final PersonRepository repo) {
		super(repo, Person.class);
	}

	@Override
	public Short getUnitIdByEntity(final Person person) {
		return person.getUnit().getId();
	}
}
