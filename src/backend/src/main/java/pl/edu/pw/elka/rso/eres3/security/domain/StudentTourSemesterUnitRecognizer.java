package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTourSemester;
import pl.edu.pw.elka.rso.eres3.domain.repositories.StudentTourSemesterRepository;

/**
 * Unit recognizer for student tour semesters.
 */
@Component
public class StudentTourSemesterUnitRecognizer extends EntityUnitRecognizer {

	private final StudentTourSemesterRepository repo;

	public StudentTourSemesterUnitRecognizer(final StudentTourSemesterRepository repo) {
		this.repo = repo;
	}

	@Override
	public Class<?> getRecognizableClass() {
		return StudentTourSemester.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final StudentTourSemester tourSemester = (StudentTourSemester) entity;
		return tourSemester.getTour().getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final StudentTourSemester studentTourSemester = repo.findOne((Long) entityId);
		return getUnitIdByEntity(studentTourSemester);
	}

}
