package pl.edu.pw.elka.rso.eres3.security.domain;

import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;

/**
 * Recognizer of organizational unit for organizational unit entity.
 *
 * Isn't very complicated, because returns exactly the same organizational unit
 * for a given organizational unit, but this class is necessary to keep the
 * convention of how organizational units are being found for entities.
 */
@Component
public class PlainUnitRecognizer extends EntityUnitRecognizer<OrganizationalUnit, Short> {

	public PlainUnitRecognizer(){
		super(null, OrganizationalUnit.class);
	}

	@Override
	public Short getUnitIdByEntity(final OrganizationalUnit unit) {
		return unit.getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Short entityId) {
		return entityId;
	}
}