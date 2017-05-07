package pl.edu.pw.elka.rso.eres3.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Permission;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PermissionRepository;

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
    @PreAuthorize("hasPermission(null, 'PermissionRead')")
    public List<Permission> getAllPermissions() {
        return getAll();
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'PermissionRead')")
    public ResponseEntity<Permission> getPermission(@PathVariable() final String id) {
        return getEntity(id);
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(null, 'PermissionCreate')")
    public ResponseEntity<Permission> addPermission(@RequestBody final Permission permission) {
        return addEntity(permission);
    }

    @RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(null, 'PermissionUpdate')")
    public ResponseEntity<Permission> updatePermission(@RequestBody final Permission permission) {
        return updateEntity(permission);
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(null, 'PermissionDelete')")
    public ResponseEntity<Permission> deletePermission(@PathVariable final String id) {
        return deleteEntity(id);
    }

    @Override
    protected String getControllerMapping() {
        return mapping;
    }
}
