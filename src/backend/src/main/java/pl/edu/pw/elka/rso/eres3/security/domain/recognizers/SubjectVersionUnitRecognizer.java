package pl.edu.pw.elka.rso.eres3.security.domain.recognizers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectVersion;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectVersionRepository;

/**
 * Unit recognizer for subject version.
 */
@Component
public class SubjectVersionUnitRecognizer extends EntityUnitRecognizer<SubjectVersion, Integer> {

	@Autowired
	public SubjectVersionUnitRecognizer(final SubjectVersionRepository repo) {
		super(repo, SubjectVersion.class);
	}

	@Override
	public Short getUnitIdByEntity(final SubjectVersion version) {
		return version.getSubject().getUnit().getId();
	}
}