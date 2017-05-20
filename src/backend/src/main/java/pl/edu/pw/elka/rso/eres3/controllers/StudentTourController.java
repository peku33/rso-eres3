package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTour;
import pl.edu.pw.elka.rso.eres3.domain.repositories.StudentTourRepository;

import javax.transaction.Transactional;

/**
 * Rest controller for student's tours.
 */
@RestController
@Transactional
public class StudentTourController extends AbstractCrudController<StudentTour, Long> {
	static final String mapping = "/studenttours";
	private final StudentTourRepository tourRepo;

	public StudentTourController(final StudentTourRepository tourRepo){
		super(tourRepo, true);
		this.tourRepo = tourRepo;
	}

	@RequestMapping(value = "persons/{id}" + mapping, method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'Person', 'StudentTourRead')")
	public Iterable<StudentTour> getAllStudentToursByPersonId(@PathVariable final short id){
		return tourRepo.findByPersonId(id);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'StudentTour', 'StudentTourRead')")
	public ResponseEntity<StudentTour> getStudentTour(@PathVariable final long id) {
		return getEntity(id);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#studentTour, 'StudentTourCreate')")
	public ResponseEntity<StudentTour> addStudentTour(@RequestBody final StudentTour studentTour) {
		return addEntity(studentTour);
	}

	@RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#studentTour, 'StudentTourUpdate')")
	public ResponseEntity<StudentTour> updateStudentTour(@RequestBody final StudentTour studentTour) {
		return updateEntity(studentTour);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'StudentTour', 'StudentTourDelete')")
	public ResponseEntity<StudentTour> deleteStudentTour(@PathVariable final long id) {
		return deleteEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}

}
