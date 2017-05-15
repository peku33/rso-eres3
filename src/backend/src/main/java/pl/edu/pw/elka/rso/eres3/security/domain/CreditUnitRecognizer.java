package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.Credit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.CreditRepository;

/**
 * Unit recognizer for credits.
 */
@Component
public class CreditUnitRecognizer extends EntityUnitRecognizer{

	private final CreditRepository repo;

	public CreditUnitRecognizer(final CreditRepository repo) {
		this.repo = repo;
	}

	@Override
	public Class<?> getRecognizableClass() {
		return Credit.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final Credit credit = (Credit) entity;
		return credit.getTour().getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final Credit credit = repo.findOne((Long) entityId);
		return getUnitIdByEntity(credit);
	}

}
