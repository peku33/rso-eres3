package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.entities.dto.PersonDto;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;
import pl.edu.pw.elka.rso.eres3.security.PersonService;
import pl.edu.pw.elka.rso.eres3.security.exceptions.LoginExistsException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
	private PersonService personService;
	
	@Autowired
	PersonController(final PersonRepository repository){
		super(repository, true);
	}

	@RequestMapping(value = mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'PersonRead')")
	public List<PersonDto> getAllPersons() {
		return getAll().stream()
				.map(person -> personService.mapPersonToDto(person))
				.collect(Collectors.toList());
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'PersonRead')")
	public ResponseEntity<PersonDto> getPerson(@PathVariable final long id) {
		PersonDto personDto = personService.mapPersonToDto(getEntity(id).getBody());
		return new ResponseEntity<PersonDto>(personDto, HttpStatus.OK);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(null, 'PersonCreate')")
	public ResponseEntity<Person> addPerson(@RequestBody final PersonDto personDto) {
		try {
			Person person = personService.registerNewPersonAccount(personDto);
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		} catch(LoginExistsException e)
		{
			return new ResponseEntity<Person>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(null, 'PersonUpdate')")
	public ResponseEntity<Person> updatePerson(@RequestBody final PersonDto personDto) {
		Person person = personService.mapPersonFromDto(personDto);
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