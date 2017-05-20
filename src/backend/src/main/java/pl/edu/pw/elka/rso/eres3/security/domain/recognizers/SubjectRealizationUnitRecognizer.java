package pl.edu.pw.elka.rso.eres3.security.domain.recognizers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectRealization;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRealizationRepository;

/**
 * Unit recognizer for subject realizations.
 */
@Component
public class SubjectRealizationUnitRecognizer extends EntityUnitRecognizer<SubjectRealization, Integer> {

	@Autowired
	public SubjectRealizationUnitRecognizer(final SubjectRealizationRepository repo) {
		super(repo, SubjectRealization.class);
	}

	@Override
	public Short getUnitIdByEntity(final SubjectRealization realization) {
		return realization.getSubjectVersion().getSubject().getUnit().getId();
	}
}