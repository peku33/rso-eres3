package pl.edu.pw.elka.rso.eres3.dto.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.dto.PersonDTO;

import java.util.stream.Collectors;

/**
 * Created by Jakub Lorenc on 23.04.17.
 */
@Component
public class PersonDTOConverter {
    private final PermissionOnUnitDTOConverter permissionOnUnitDTOConverter;

    @Autowired
    PersonDTOConverter(PermissionOnUnitDTOConverter permissionOnUnitDTOConverter){
        this.permissionOnUnitDTOConverter = permissionOnUnitDTOConverter;
    }

    public PersonDTO getPersonDTO(final Person person){
        final PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setOtherNames(person.getOtherNames());
        dto.setLastName(person.getLastName());
        dto.setLogin(person.getLogin());
        dto.setPassword(person.getPassword());
        dto.setPesel(person.getPesel());
        if(person.getPermissions() != null)
            dto.setPermissions(
                    person.getPermissions()
                    .stream()
                    .map(permissionOnUnitDTOConverter::getPermissionOnUnitDTO)
                    .collect(Collectors.toList())
            );
        return dto;
    }

    public Person getPerson(final PersonDTO dto) {
        final Person person = new Person();
        person.setId(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setOtherNames(dto.getOtherNames());
        person.setLastName(dto.getLastName());
        person.setLogin(dto.getLogin());
        person.setPassword(dto.getPassword());
        person.setPesel(dto.getPesel());
        if(dto.getPermissions() != null)
            person.setPermissions(dto.getPermissions()
                    .stream()
                    .map(permission -> permissionOnUnitDTOConverter.getPersonPermissionOnUnit(permission, person))
                    .collect(Collectors.toSet())
            );
        return person;
    }
}
