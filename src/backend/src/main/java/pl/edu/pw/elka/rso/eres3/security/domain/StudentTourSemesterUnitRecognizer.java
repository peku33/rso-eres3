package pl.edu.pw.elka.rso.eres3.security.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTourSemester;
import pl.edu.pw.elka.rso.eres3.domain.repositories.StudentTourSemesterRepository;

/**
 * Unit recognizer for student tour semesters.
 */
@Component
public class StudentTourSemesterUnitRecognizer extends EntityUnitRecognizer<StudentTourSemester, Long> {

	@Autowired
	public StudentTourSemesterUnitRecognizer(final StudentTourSemesterRepository repo) {
		super(repo, StudentTourSemester.class);
	}

	@Override
	public Short getUnitIdByEntity(final StudentTourSemester tourSemester) {
		return tourSemester.getTour().getUnit().getId();
	}
}
