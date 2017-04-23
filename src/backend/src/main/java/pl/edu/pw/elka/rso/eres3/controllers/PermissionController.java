package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Permission;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PermissionRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Rest controller for permission.
 *
 * In first version the unchecked validation exceptions thrown by JPA are transmitted to frontend.
 * This may be a subject to change in the future if needed.
 *
 * Created by Jakub Lorenc on 23.04.17.
 */
@RestController
@Transactional
public class PermissionController extends AbstractCrudController<Permission, String> {
    private static final String mapping = "/permissions";

    @Autowired
    PermissionController(final PermissionRepository repository) {
        super(repository, false);
    }

    @RequestMapping(value = mapping, method = RequestMethod.GET)
    public List<Permission> getAllPermissions() {
        return getAll();
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Permission> getPermission(@PathVariable() String id) {
        return getEntity(id);
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    public ResponseEntity<Permission> addPermission(@RequestBody Permission permission) {
        return addEntity(permission);
    }

    @RequestMapping(value = mapping, method = RequestMethod.PUT)
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission) {
        return updateEntity(permission);
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePermission(@PathVariable String id) {
        return deleteEntity(id);
    }

    @Override
    protected String getControllerMapping() {
        return mapping;
    }
}
