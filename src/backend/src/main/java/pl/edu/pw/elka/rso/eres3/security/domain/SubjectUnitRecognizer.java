package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRepository;

/**
 * Unit recognizer for subjects.
 */
@Component
public class SubjectUnitRecognizer extends EntityUnitRecognizer {

	private final SubjectRepository subjectRepo;

	@Autowired
	public SubjectUnitRecognizer(final SubjectRepository subjectRepo) {
		this.subjectRepo = subjectRepo;
	}

	@Override
	public Class<?> getRecognizableClass() {
		return Subject.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final Subject subject = (Subject) entity;
		return subject.getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final Subject subject = subjectRepo.findOne((int)entityId);
		return getUnitIdByEntity(subject);
	}
}
