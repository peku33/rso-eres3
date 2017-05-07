package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;

/**
 * Abstract class that recognizes an {@link OrganizationalUnit} object for entities of specified type.
 * Used for checking user's permission's on domain objects of specified type.
 */
public abstract class EntityUnitRecognizer {

	public abstract Class<?> getRecognizableClass();

	public abstract Short getUnitIdByEntity(Object entity);

	public abstract Short getUnitIdByEntityId(Serializable entityId);
}