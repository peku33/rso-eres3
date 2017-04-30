package pl.edu.pw.elka.rso.eres3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Semester;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SemesterRepository;

/**
 * Rest controller for semesters, read only operations.
 */
@RestController
public class SemesterController extends AbstractCrudController<Semester, Short> {
	private static final String mapping = "/semesters";

	@Autowired
	public SemesterController(final SemesterRepository semesterRepository) {
		super(semesterRepository, true);
	}

	@RequestMapping(value = mapping, method = RequestMethod.GET)
	public List<Semester> getAllSemesters() {
		return getAll();
	}

	@RequestMapping(value = mapping + "{id}", method = RequestMethod.GET)
	public ResponseEntity<Semester> getSemester(@PathVariable final short id) {
		return getEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}
}
