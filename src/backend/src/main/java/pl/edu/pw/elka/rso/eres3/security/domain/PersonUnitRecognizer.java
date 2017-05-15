package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

/**
 * Unit recognizer for persons.
 */
@Component
public class PersonUnitRecognizer extends EntityUnitRecognizer{

	private final PersonRepository repo;

	public PersonUnitRecognizer(final PersonRepository repo) {
		this.repo = repo;
	}

	@Override
	public Class<?> getRecognizableClass() {
		return Person.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final Person person = (Person) entity;
		return person.getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final Person person = repo.findOne((Long) entityId);
		return getUnitIdByEntity(person);
	}

}
