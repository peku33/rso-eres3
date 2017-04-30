package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.elka.rso.eres3.domain.compositeids.GrantedPermissionId;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.entities.Permission;
import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.generators.GrantedPermissionGenerator;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
public class GrantedPermissionControllerTest {
    private GrantedPermissionRepository repository;
    private GrantedPermissionGenerator generator;
    private GrantedPermissionController controller;

    private GrantedPermission grantedPermission1;
    private GrantedPermission grantedPermission2;

    private GrantedPermissionId correctId;
    private GrantedPermissionId wrongId;
    private GrantedPermissionId nullId;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(GrantedPermissionRepository.class);
        generator = Mockito.mock(GrantedPermissionGenerator.class);
        controller = new GrantedPermissionController(repository, generator);

        grantedPermission1 = Mockito.mock(GrantedPermission.class);
        grantedPermission2 = Mockito.mock(GrantedPermission.class);
        assertNotEquals(grantedPermission1, grantedPermission2);

        correctId = new GrantedPermissionId();
        correctId.setPermission("");
        correctId.setPerson(1L);
        correctId.setUnit((short)1);
        wrongId = new GrantedPermissionId();
        wrongId.setPermission("a");
        wrongId.setPerson(2L);
        wrongId.setUnit((short)2);
        nullId = new GrantedPermissionId();
    }

    @Test
    public void getAllGrantedPermissions() throws Exception {
        final Set<GrantedPermission> grantedPermissionSet = ImmutableSet.of(
                grantedPermission1,
                grantedPermission2
        );
        Mockito.when(repository.findAll()).thenReturn(grantedPermissionSet);
        assertEquals(grantedPermissionSet, ImmutableSet.copyOf(controller.getAllGrantedPermissions()));
    }

    @Test
    public void getGrantedPermissionsByPersonId() throws Exception {
        final List<GrantedPermission> grantedPermissionList = Collections.singletonList(grantedPermission1);
        final Long correctId = 1L;
        final Long wrongId = 2L;
        Mockito.when(repository.getGrantedPermissionsByPersonId(correctId)).thenReturn(grantedPermissionList);
        Mockito.when(repository.getGrantedPermissionsByPersonId(wrongId)).thenReturn(Collections.emptyList());

        assertEquals(grantedPermissionList, controller.getGrantedPermissionsByPersonId(1L));
        assertTrue(controller.getGrantedPermissionsByPersonId(2L).isEmpty());
    }

    @Test
    public void grantPermission() throws Exception {
        Mockito.when(repository.save(grantedPermission1)).thenReturn(grantedPermission2);
        Mockito.when(grantedPermission1.getPermission()).thenReturn(new Permission());
        Mockito.when(grantedPermission1.getPerson()).thenReturn(new Person());
        Mockito.when(grantedPermission1.getUnit()).thenReturn(new OrganizationalUnit());
        Mockito.when(generator.byId(correctId)).thenReturn(grantedPermission1);
        Mockito.when(generator.byId(wrongId)).thenReturn(grantedPermission2);

        assertEquals(controller.badRequest, controller.grantPermission(nullId));
        assertEquals(controller.badRequest, controller.grantPermission(null));
        assertEquals(controller.notFound, controller.grantPermission(wrongId));
        final ResponseEntity<GrantedPermission> response = controller.grantPermission(correctId);
        assertEquals(URI.create(GrantedPermissionController.mapping), response.getHeaders().getLocation());
        assertEquals(grantedPermission2, response.getBody());
    }

    @Test
    public void denyPermission() throws Exception {
        Mockito.when(repository.exists(wrongId)).thenReturn(false);
        Mockito.when(repository.exists(correctId)).thenReturn(true);

        assertEquals(controller.badRequest, controller.denyPermission(nullId));
        assertEquals(controller.badRequest, controller.denyPermission(null));
        assertEquals(controller.notFound, controller.denyPermission(wrongId));
        assertEquals(controller.noContent, controller.denyPermission(correctId));
    }
}