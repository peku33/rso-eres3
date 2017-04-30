package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.compositeids.GrantedPermissionId;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;

import java.util.List;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
public interface GrantedPermissionRepository extends CrudRepository<GrantedPermission, GrantedPermissionId> {
    List<GrantedPermission> getGrantedPermissionsByPersonId(final Long id);
}
