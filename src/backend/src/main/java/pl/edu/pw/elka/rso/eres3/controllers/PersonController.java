package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

import javax.transaction.Transactional;
import java.net.URI;
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
public class PersonController {
    private final PersonRepository personRepository;

    @Autowired
    PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> getAllPersons() {
        return Lists.newArrayList(personRepository.findAll());
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable() long id) {
        final Person person = personRepository.findOne(id);
        if(person == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        if(person.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        final Person createdPerson = personRepository.save(person);
        return ResponseEntity.created(URI.create("/persons/" + createdPerson.getId())).body(createdPerson);
    }

    @RequestMapping(value = "/persons", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        if(person.getId() == null){
            return ResponseEntity.badRequest().build();
        }
        if(!personRepository.exists(person.getId())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personRepository.save(person));
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {
        if(!personRepository.exists(id)){
            return ResponseEntity.notFound().build();
        }
        personRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
