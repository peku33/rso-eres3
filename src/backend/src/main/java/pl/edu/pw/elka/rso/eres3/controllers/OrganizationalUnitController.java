package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.OrganizationalUnitRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Rest controller for organizational unit.
 *
 * In first version the unchecked validation exceptions thrown by JPA are transmitted to frontend.
 * This may be a subject to change in the future if needed.
 *
 * Created by Jakub Lorenc on 23.04.17.
 */
@RestController
@Transactional
public class OrganizationalUnitController extends AbstractCrudController<OrganizationalUnit, Short> {
    private static final String mapping = "/units";

    @Autowired
    OrganizationalUnitController(final OrganizationalUnitRepository repository){
        super(repository, true);
    }

    @RequestMapping(value = mapping, method = RequestMethod.GET)
    public List<OrganizationalUnit> getAllUnits() {
        return getAll();
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'OrganizationalUnit', 'read')")
    public ResponseEntity<OrganizationalUnit> getUnit(@PathVariable final short id) {
        return getEntity(id);
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    public ResponseEntity<OrganizationalUnit> addUnit(@RequestBody OrganizationalUnit unit) {
        return addEntity(unit);
    }

    @RequestMapping(value = mapping, method = RequestMethod.PUT)
    public ResponseEntity<OrganizationalUnit> updateUnit(@RequestBody OrganizationalUnit unit) {
        return updateEntity(unit);
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUnit(@PathVariable short id) {
        return deleteEntity(id);
    }

    @Override
    protected String getControllerMapping() {
        return mapping;
    }
}
