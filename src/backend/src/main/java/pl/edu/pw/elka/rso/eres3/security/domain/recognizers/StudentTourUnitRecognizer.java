package pl.edu.pw.elka.rso.eres3.security.domain.recognizers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTour;
import pl.edu.pw.elka.rso.eres3.domain.repositories.StudentTourRepository;

/**
 * Unit recognizer for student's tours.
 */
@Component
public class StudentTourUnitRecognizer extends EntityUnitRecognizer<StudentTour, Long> {

	@Autowired
	public StudentTourUnitRecognizer(final StudentTourRepository repo) {
		super(repo, StudentTour.class);
	}

	@Override
	public Short getUnitIdByEntity(final StudentTour tour) {
		return tour.getUnit().getId();
	}
}
