package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

import java.net.URI;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Person rest controller tests.
 *
 * Created by Jakub Lorenc on 22.04.17.
 */
public class PersonControllerTest {
    private static final ResponseEntity badRequest = ResponseEntity.badRequest().build();
    private static final ResponseEntity notFound = ResponseEntity.notFound().build();

    private PersonRepository repository;
    private PersonController controller;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(PersonRepository.class);
        controller = new PersonController(repository);
    }

    @Test
    public void getAllPersons() throws Exception {
        final Set<Person> personSet = ImmutableSet.of(
                new Person(1L),
                new Person(2L)
        );
        Mockito.when(repository.findAll()).thenReturn(personSet);
        assertEquals(personSet, ImmutableSet.copyOf(controller.getAllPersons()));
    }

    @Test
    public void getPerson() throws Exception {
        final Person person = new Person(1L);
        Mockito.when(repository.findOne(1L)).thenReturn(person);
        Mockito.when(repository.findOne(2L)).thenReturn(null);

        assertEquals(person, controller.getPerson(1L).getBody());
        assertEquals(notFound, controller.getPerson(2L));
    }

    @Test
    public void addPerson() throws Exception {
        final Person personWithId = new Person(1L);
        final Person newPerson = new Person();
        Mockito.when(repository.save(newPerson)).thenReturn(new Person(2L));

        assertEquals(badRequest, controller.addPerson(personWithId));

        final ResponseEntity<Person> goodRequest = controller.addPerson(newPerson);
        assertEquals(Long.valueOf(2L), goodRequest.getBody().getId());
        assertEquals(URI.create("/persons/2"), goodRequest.getHeaders().getLocation());
    }

    @Test
    public void updatePerson() throws Exception {
        final Person personWithoutId = new Person();
        final Person person = new Person(1L);
        final Person nonExistingPerson = new Person(2L);
        Mockito.when(repository.exists(person.getId())).thenReturn(true);
        Mockito.when(repository.exists(nonExistingPerson.getId())).thenReturn(false);
        Mockito.when(repository.save(person)).thenReturn(new Person(1L));

        assertEquals(badRequest, controller.updatePerson(personWithoutId));
        assertEquals(notFound, controller.updatePerson(nonExistingPerson));

        final ResponseEntity<Person> goodRequest = controller.updatePerson(person);
        assertEquals(person.getId(), goodRequest.getBody().getId());
        assertTrue(goodRequest.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deletePerson() throws Exception {
        Mockito.when(repository.exists(1L)).thenReturn(true);
        Mockito.when(repository.exists(2L)).thenReturn(false);

        assertEquals(notFound, controller.deletePerson(2L));
        assertTrue(controller.deletePerson(1).getStatusCode().is2xxSuccessful());
    }

}