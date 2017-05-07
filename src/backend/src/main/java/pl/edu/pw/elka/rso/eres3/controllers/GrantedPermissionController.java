package pl.edu.pw.elka.rso.eres3.controllers;

import java.net.URI;
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

import com.google.common.collect.Lists;

import pl.edu.pw.elka.rso.eres3.domain.compositeids.GrantedPermissionId;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.generators.GrantedPermissionGenerator;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
@RestController
@Transactional
public class GrantedPermissionController {
    static final String mapping = "permissions/granted";

    final ResponseEntity<GrantedPermission> notFound = ResponseEntity.notFound().build();
    final ResponseEntity<GrantedPermission> badRequest = ResponseEntity.badRequest().build();
    final ResponseEntity<GrantedPermission> noContent = ResponseEntity.noContent().build();

    private final GrantedPermissionRepository repository;
    private final GrantedPermissionGenerator generator;

    @Autowired
    GrantedPermissionController(final GrantedPermissionRepository repository,
                                final GrantedPermissionGenerator generator){
        this.repository = repository;
        this.generator = generator;
    }

    @RequestMapping(value = mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'GrantedPermissionRead')")
    public List<GrantedPermission> getAllGrantedPermissions(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value = mapping + "/person/{id}")
    @PreAuthorize("hasPermission(null, 'GrantedPermissionRead')")
    public List<GrantedPermission> getGrantedPermissionsByPersonId(@PathVariable final long id){
        return repository.getGrantedPermissionsByPersonId(id);
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(null, 'GrantedPermissionCreate')")
    public ResponseEntity<GrantedPermission> grantPermission(@RequestBody final GrantedPermissionId id){
        if(isNull(id)){
            return badRequest;
        }
        final GrantedPermission toCreate = generator.byId(id);
        if(toCreate.getPermission() == null || toCreate.getPerson() == null || toCreate.getUnit() == null){
            return notFound;
        }
        return ResponseEntity.created(URI.create(mapping)).body(repository.save(toCreate));
    }

    @RequestMapping(value = mapping, method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(null, 'GrantedPermissionDelete')")
    public ResponseEntity<GrantedPermission> denyPermission(@RequestBody final GrantedPermissionId id){
        if(isNull(id)) {
            return badRequest;
        }
        if(!repository.exists(id)){
            return notFound;
        }
        repository.delete(id);
        return noContent;
    }

    private boolean isNull(final GrantedPermissionId id){
        return id == null || id.getPermission() == null || id.getPerson() == null || id.getUnit() == null;
    }
}
