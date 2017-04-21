package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;

/**
 * Created by Jakub Lorenc on 21.04.17.
 */
public interface OrganizationalUnitRepository extends CrudRepository<OrganizationalUnit, String> {
}
