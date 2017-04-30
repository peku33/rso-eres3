package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;

import java.util.List;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
public class PersonPermissionOnUnitController {
    private GrantedPermissionRepository repository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<GrantedPermission> getAll(){
        return Lists.newArrayList(repository.findAll());
    }
}
