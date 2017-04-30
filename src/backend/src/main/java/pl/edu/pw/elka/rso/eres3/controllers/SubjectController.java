package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

/**
 * Rest controller for subjects.
 */
@RestController
@Transactional
public class SubjectController {
	private final SubjectRepository subjectRepository;

	@Autowired
	public SubjectController(final SubjectRepository subjectRepository) {
		this.subjectRepository = subjectRepository;
	}

	@RequestMapping(value = "/subjects/unit/{id}", method = RequestMethod.GET)
	public List<Subject> getAllSubjectsOnUnit(@PathVariable final short id) {
		return Lists.newArrayList(subjectRepository.findByUnitId(id));
	}

	@RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET)
	public ResponseEntity<Subject> getSubject(@PathVariable final int id){
		final Subject subject = subjectRepository.findOne(id);
		if(subject == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(subject);
	}

	@RequestMapping(value = "/subjects", method = RequestMethod.POST)
	public ResponseEntity<Subject> addSubject(@RequestBody final Subject subject){
		if(subject.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		final Subject createdSubject = subjectRepository.save(subject);
		return ResponseEntity.created(URI.create("/subjects/" + createdSubject.getId())).body(createdSubject);
	}

	@RequestMapping(value = "/subjects", method = RequestMethod.PUT)
	public ResponseEntity<Subject> updateSubject(@RequestBody final Subject subject) {
		if(subject.getId() == null){
			return ResponseEntity.badRequest().build();
		}
		if(!subjectRepository.exists(subject.getId())){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(subjectRepository.save(subject));
	}

	@RequestMapping(value = "/subjects/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Subject> deleteSubject(@PathVariable final int id) {
		if(!subjectRepository.exists(id)){
			return ResponseEntity.notFound().build();
		}
		subjectRepository.delete(id);
		return ResponseEntity.noContent().build();
	}
}