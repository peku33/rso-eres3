package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;
import pl.edu.pw.elka.rso.eres3.dto.PersonDTO;
import pl.edu.pw.elka.rso.eres3.dto.converters.PersonDTOConverter;

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
    private static final String mapping = "/persons";

    private final PersonDTOConverter converter;

    @Autowired
    PersonController(final PersonRepository personRepository, final PersonDTOConverter converter){
        super(personRepository, true);
        this.converter = converter;
    }

    @RequestMapping(value = mapping, method = RequestMethod.GET)
    public List<PersonDTO> getAllPersons() {
        return getAll()
                .stream()
                .map(converter::getPersonDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonDTO> getPerson(@PathVariable() long id) {
        return getPersonDTOResponseEntity(getEntity(id));
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO dto) {
        final Person person = converter.getPerson(dto);
        return getPersonDTOResponseEntity(addEntity(person));
    }

    @RequestMapping(value = mapping, method = RequestMethod.PUT)
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO dto) {
        final Person person = converter.getPerson(dto);
        return getPersonDTOResponseEntity(updateEntity(person));
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePerson(@PathVariable long id) {
        return deleteEntity(id);
    }

    @Override
    protected String getControllerMapping() {
        return mapping;
    }

    private ResponseEntity<PersonDTO> getPersonDTOResponseEntity(ResponseEntity<Person> response) {
        ResponseEntity.BodyBuilder builder = ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders());
        if(response.hasBody()){
            return builder.body(converter.getPersonDTO(response.getBody()));
        }
        return builder.build();
    }
}
