package pl.edu.pw.elka.rso.eres3.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRepository;

/**
 * Rest controller for subjects.
 */
@RestController
@Transactional
public class SubjectController extends AbstractCrudController<Subject, Integer> {
	private static final String mapping = "/subjects";
	private final SubjectRepository subjectRepository;

	@Autowired
	public SubjectController(final SubjectRepository subjectRepository) {
		super(subjectRepository, true);
		this.subjectRepository = subjectRepository;
	}

	@RequestMapping(value =  "/units/{id}/subjects", method = RequestMethod.GET)
	public Iterable<Subject> getAllSubjectsOnUnit(@PathVariable final short id) {
		return subjectRepository.findByUnitId(id);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Subject> getSubject(@PathVariable final int id){
		return getEntity(id);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
	public ResponseEntity<Subject> addSubject(@RequestBody final Subject subject){
		return addEntity(subject);
	}

	@RequestMapping(value = mapping, method = RequestMethod.PUT)
	public ResponseEntity<Subject> updateSubject(@RequestBody final Subject subject) {
		return updateEntity(subject);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Subject> deleteSubject(@PathVariable final int id) {
		return deleteEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}
}