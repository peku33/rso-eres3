package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectRealization;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRealizationRepository;

/**
 * Unit recognizer for subject realizations.
 */
@Component
public class SubjectRealizationUnitRecognizer extends EntityUnitRecognizer {

	private final SubjectRealizationRepository realizationRepo;

	public SubjectRealizationUnitRecognizer (final SubjectRealizationRepository realizationRepo){
		this.realizationRepo = realizationRepo;
	}

	@Override
	public Class<?> getRecognizableClass() {
		return SubjectRealization.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final SubjectRealization realization = (SubjectRealization) entity;
		return realization.getSubjectVersion().getSubject().getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final SubjectRealization realization = realizationRepo.findOne((int) entityId);
		return getUnitIdByEntityId(realization);
	}
}