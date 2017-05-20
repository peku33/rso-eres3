package pl.edu.pw.elka.rso.eres3.security.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SpecializationRepository;

/**
 * Unit recognizer for specializations.
 */
@Component
public class SpecializationUnitRecognizer extends EntityUnitRecognizer<Specialization, Integer> {

	@Autowired
	public SpecializationUnitRecognizer(final SpecializationRepository repo) {
		super(repo, Specialization.class);
	}

	@Override
	public Short getUnitIdByEntity(final Specialization spec) {
		return spec.getUnit().getId();
	}
}