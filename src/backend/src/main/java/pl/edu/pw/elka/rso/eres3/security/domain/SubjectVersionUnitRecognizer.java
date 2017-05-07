package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectVersion;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectVersionRepository;

/**
 * Unit recognizer for subject version.
 */
@Component
public class SubjectVersionUnitRecognizer extends EntityUnitRecognizer {

	private final SubjectVersionRepository versionRepo;

	@Autowired
	public SubjectVersionUnitRecognizer(final SubjectVersionRepository versionRepo) {
		this.versionRepo = versionRepo;
	}

	@Override
	public Class<?> getRecognizableClass() {
		return SubjectVersion.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final SubjectVersion version = (SubjectVersion) entity;
		return version.getSubject().getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final SubjectVersion version = versionRepo.findOne((int) entityId);
		return getUnitIdByEntity(version);
	}
}