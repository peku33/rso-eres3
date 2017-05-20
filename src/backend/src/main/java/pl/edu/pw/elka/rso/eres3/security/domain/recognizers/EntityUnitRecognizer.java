package pl.edu.pw.elka.rso.eres3.security.domain.recognizers;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;

import java.io.Serializable;

/**
 * Abstract class that recognizes an {@link OrganizationalUnit} object for entities of specified type.
 * Used for checking user's permission's on domain objects of specified type.
 */
public abstract class EntityUnitRecognizer<T, ID extends Serializable> {

	private final CrudRepository<T, ID> repository;
	private final Class<T> clazz;

	EntityUnitRecognizer(final CrudRepository<T, ID> repository, final Class<T> clazz){
		this.repository = repository;
		this.clazz = clazz;
	}

	public final Class<T> getRecognizableClass(){
		return clazz;
	}

	public Short getUnitIdByEntityId(ID entityId){
		final T entity = repository.findOne(entityId);
		return getUnitIdByEntity(entity);
	}

	public abstract Short getUnitIdByEntity(T entity);
}