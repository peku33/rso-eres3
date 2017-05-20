package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTourSemester;
import pl.edu.pw.elka.rso.eres3.domain.repositories.StudentTourSemesterRepository;

import javax.transaction.Transactional;

/**
 * Rest controller for student tour semesters.
 */
@RestController
@Transactional
public class StudentTourSemesterController extends AbstractCrudController<StudentTourSemester, Long> {
	private static final String mapping = "/studenttoursemesters";
	private final StudentTourSemesterRepository studentTourSemesterRepo;

	public StudentTourSemesterController(final StudentTourSemesterRepository studentTourSemesterRepo) {
		super(studentTourSemesterRepo, true);
		this.studentTourSemesterRepo = studentTourSemesterRepo;
	}

	@RequestMapping(value = StudentTourController.mapping + "/{id}" + mapping, method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'StudentTour', 'StudentTourSemesterRead')")
	public Iterable<StudentTourSemester> getAllStudentToursSemestersByTourId(@PathVariable final short id){
		return studentTourSemesterRepo.findByTourId(id);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'StudentTourSemester', 'StudentTourSemesterRead')")
	public ResponseEntity<StudentTourSemester> getStudentTourSemester(@PathVariable final long id) {
		return getEntity(id);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#studentTourSemester, 'StudentTourSemesterCreate')")
	public ResponseEntity<StudentTourSemester> addStudentTourSemester(@RequestBody final StudentTourSemester studentTourSemester) {
		return addEntity(studentTourSemester);
	}

	@RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#studentTourSemester, 'StudentTourSemesterUpdate')")
	public ResponseEntity<StudentTourSemester> updateStudentTourSemester(@RequestBody final StudentTourSemester studentTourSemester) {
		return updateEntity(studentTourSemester);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'StudentTourSemester', 'StudentTourSemesterDelete')")
	public ResponseEntity<StudentTourSemester> deleteStudentTourSemester(@PathVariable final long id) {
		return deleteEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}

}
