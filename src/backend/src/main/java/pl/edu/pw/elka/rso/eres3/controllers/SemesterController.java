package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Semester;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SemesterRepository;

import java.util.List;

/**
 * Rest controller for semesters, read only operations.
 */
@RestController
public class SemesterController {

	private final SemesterRepository semesterRepository;

	@Autowired
	public SemesterController(final SemesterRepository semesterRepository) {
		this.semesterRepository = semesterRepository;
	}

	@RequestMapping(value = "/semesters", method = RequestMethod.GET)
	public List<Semester> getAllSemesters() {
		return Lists.newArrayList(semesterRepository.findAll());
	}

	@RequestMapping(value = "/semesters/{id}", method = RequestMethod.GET)
	public ResponseEntity<Semester> getSemester(@PathVariable final short id) {
		final Semester semester = semesterRepository.findOne(id);
		if(semester == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(semester);
	}
}
