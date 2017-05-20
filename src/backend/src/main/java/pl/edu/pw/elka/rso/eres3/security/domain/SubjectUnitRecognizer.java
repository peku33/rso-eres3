package pl.edu.pw.elka.rso.eres3.security.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRepository;

/**
 * Unit recognizer for subjects.
 */
@Component
public class SubjectUnitRecognizer extends EntityUnitRecognizer<Subject, Integer> {

	@Autowired
	public SubjectUnitRecognizer(final SubjectRepository subjectRepo) {
		super(subjectRepo, Subject.class);
	}

	@Override
	public Short getUnitIdByEntity(final Subject subject) {
		return subject.getUnit().getId();
	}
}
