package pl.edu.pw.elka.rso.eres3.domain.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.compositeids.GrantedPermissionId;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.repositories.OrganizationalUnitRepository;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PermissionRepository;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
@Component
public class GrantedPermissionGenerator {
    private final PersonRepository personRepository;
    private final PermissionRepository permissionRepository;
    private final OrganizationalUnitRepository unitRepository;

    @Autowired
    public GrantedPermissionGenerator(
            final PersonRepository personRepository,
            final PermissionRepository permissionRepository,
            final OrganizationalUnitRepository unitRepository) {
        this.personRepository = personRepository;
        this.permissionRepository = permissionRepository;
        this.unitRepository = unitRepository;
    }

    public GrantedPermission byId(GrantedPermissionId id){
        final GrantedPermission grantedPermission = new GrantedPermission();
        grantedPermission.setPermission(permissionRepository.findOne(id.getPermission()));
        grantedPermission.setPerson(personRepository.findOne(id.getPerson()));
        grantedPermission.setUnit(unitRepository.findOne(id.getUnit()));
        return grantedPermission;
    }
}
