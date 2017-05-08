package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SpecializationRepository;

/**
 * Unit recognizer for specializations.
 */
@Component
public class SpecializationUnitRecognizer extends EntityUnitRecognizer {

	private final SpecializationRepository specializationRepo;

	public SpecializationUnitRecognizer(final SpecializationRepository specializationRepo) {
		this.specializationRepo = specializationRepo;
	}

	@Override
	public Class<?> getRecognizableClass() {
		return Specialization.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final Specialization spec = (Specialization) entity;
		return spec.getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final Specialization spec = specializationRepo.findOne((Integer) entityId);
		return getUnitIdByEntity(spec);
	}
}