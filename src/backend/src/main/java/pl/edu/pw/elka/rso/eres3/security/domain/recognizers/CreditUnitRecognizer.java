package pl.edu.pw.elka.rso.eres3.security.domain.recognizers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.Credit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.CreditRepository;

/**
 * Unit recognizer for credits.
 */
@Component
public class CreditUnitRecognizer extends EntityUnitRecognizer<Credit, Long>{

	@Autowired
	public CreditUnitRecognizer(final CreditRepository repo) {
		super(repo, Credit.class);
	}

	@Override
	public Short getUnitIdByEntity(final Credit credit) {
		return credit.getTour().getUnit().getId();
	}
}
