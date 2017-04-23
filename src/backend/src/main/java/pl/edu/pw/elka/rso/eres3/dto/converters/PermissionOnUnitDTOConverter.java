package pl.edu.pw.elka.rso.eres3.dto.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.entities.PersonPermissionOnUnit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.OrganizationalUnitRepository;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PermissionRepository;
import pl.edu.pw.elka.rso.eres3.dto.PermissionOnUnitDTO;

/**
 * Created by Jakub Lorenc on 23.04.17.
 */
@Component
public class PermissionOnUnitDTOConverter {
    private final PermissionRepository permissionRepository;
    private final OrganizationalUnitRepository unitRepository;

    @Autowired
    public PermissionOnUnitDTOConverter(
            final PermissionRepository permissionRepository,
            final OrganizationalUnitRepository unitRepository) {
        this.permissionRepository = permissionRepository;
        this.unitRepository = unitRepository;
    }

    public PermissionOnUnitDTO getPermissionOnUnitDTO(PersonPermissionOnUnit personPermissionOnUnit) {
        final PermissionOnUnitDTO dto = new PermissionOnUnitDTO();
        dto.setPermissionName(personPermissionOnUnit.getPermission().getName());
        dto.setUnitId(personPermissionOnUnit.getUnit().getId());
        return dto;
    }

    public PersonPermissionOnUnit getPersonPermissionOnUnit(PermissionOnUnitDTO dto, Person person) {
        final PersonPermissionOnUnit personPermissionOnUnit = new PersonPermissionOnUnit();
        personPermissionOnUnit.setPermission(permissionRepository.findOne(dto.getPermissionName()));
        personPermissionOnUnit.setUnit(unitRepository.findOne(dto.getUnitId()));
        personPermissionOnUnit.setPerson(person);
        return personPermissionOnUnit;
    }
}
