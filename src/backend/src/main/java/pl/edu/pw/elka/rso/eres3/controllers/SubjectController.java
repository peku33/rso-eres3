package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRepository;

import javax.transaction.Transactional;

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

	@RequestMapping(value = OrganizationalUnitController.mapping + "/{id}" + mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'OrganizationalUnit', 'SubjectRead')")
	public Iterable<Subject> getAllSubjectsOnUnit(@PathVariable final short id) {
		return subjectRepository.findByUnitId(id);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'Subject', 'SubjectRead')")
	public ResponseEntity<Subject> getSubject(@PathVariable final int id){
		return getEntity(id);
	}

	@RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#subject, 'SubjectCreate')")
	public ResponseEntity<Subject> addSubject(@RequestBody final Subject subject){
		return addEntity(subject);
	}

	@RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#subject, 'SubjectUpdate')")
	public ResponseEntity<Subject> updateSubject(@RequestBody final Subject subject) {
		return updateEntity(subject);
	}

	@RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'Subject', 'SubjectDelete')")
	public ResponseEntity<Subject> deleteSubject(@PathVariable final int id) {
		return deleteEntity(id);
	}

	@Override
	protected String getControllerMapping() {
		return mapping;
	}
}