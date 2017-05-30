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
    static final String mapping = "/units";

    @Autowired
    OrganizationalUnitController(final OrganizationalUnitRepository repository){
        super(repository, true);
    }

    @RequestMapping(value = mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'OrganizationalUnitRead')")
    public List<OrganizationalUnit> getAllUnits() {
        return getAll();
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'OrganizationalUnit', 'OrganizationalUnitRead')")
    public ResponseEntity<OrganizationalUnit> getUnit(@PathVariable final short id) {
        return getEntity(id);
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(null, 'OrganizationalUnitCreate')")
    public ResponseEntity<OrganizationalUnit> addUnit(@RequestBody final OrganizationalUnit unit) {
        return addEntity(unit);
    }

    @RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#unit.id, 'OrganizationalUnit', 'OrganizationalUnitUpdate')")
    public ResponseEntity<OrganizationalUnit> updateUnit(@RequestBody final OrganizationalUnit unit) {
        return updateEntity(unit);
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'OrganizationalUnit', 'OrganizationalUnitDelete')")
    public ResponseEntity<OrganizationalUnit> deleteUnit(@PathVariable final short id) {
        return deleteEntity(id);
    }

    @Override
    protected String getControllerMapping() {
        return mapping;
    }
}
