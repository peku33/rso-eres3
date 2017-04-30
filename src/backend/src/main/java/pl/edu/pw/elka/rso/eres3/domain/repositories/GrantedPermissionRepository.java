package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.compositeids.PersonPermissionOnUnitId;
import pl.edu.pw.elka.rso.eres3.domain.entities.PersonPermissionOnUnit;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
public interface PersonPermissionOnUnitRepository extends CrudRepository<PersonPermissionOnUnit, PersonPermissionOnUnitId> {
}
