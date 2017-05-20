package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Rest controller for person.
 *
 * In first version the unchecked validation exceptions thrown by JPA are transmitted to frontend.
 * This may be a subject to change in the future if needed.
 *
 * Created by Jakub Lorenc on 22.04.17.
 */
@RestController
@Transactional
public class PersonController extends AbstractCrudController<Person, Long> {
	static final String mapping = "/persons";

	@Autowired
	PersonController(final PersonRepository repository){
		super(repository, true);
	}

	@RequestMapping(value = mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'PersonRead')")
	public List<Person> getAllPersons() {
		return getAll();
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'PersonRead')")
	public ResponseEntity<Person> getPerson(@PathVariable final long id) {
		return getEntity(id);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(null, 'PersonCreate')")
	public ResponseEntity<Person> addPerson(@RequestBody final Person person) {
		return addEntity(person);
	}

	@RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(null, 'PersonUpdate')")
	public ResponseEntity<Person> updatePerson(@RequestBody final Person person) {
		return updateEntity(person);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(null, 'PersonDelete')")
	public ResponseEntity<Person> deletePerson(@PathVariable final long id) {
		return deleteEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}
}