package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pw.elka.rso.eres3.domain.entities.Permission;

import static org.junit.Assert.*;

/**
 * Example test showing usage of Spring JPA repositories
 *
 * Created by Jakub Lorenc on 21.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository repository;

    private Permission permission;

    @Before
    public void setUp() throws Exception {
        permission = new Permission();
        permission.setDescription("description");
        permission.setName("name");
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void Create(){
        assertFalse(repository.exists(permission.getName()));

        repository.save(permission);
        assertTrue(repository.exists(permission.getName()));
    }

    @Test
    public void Read(){
        repository.save(permission);

        final Permission dbPermission = repository.findOne(permission.getName());
        assertEquals(permission, dbPermission);
    }

    @Test
    public void Update(){
        repository.save(permission);
        String permissionDescription = permission.getDescription();
        String newPermissionDescription = "newDescription";
        permission.setDescription(newPermissionDescription);

        final Permission oldPermission = repository.findOne(permission.getName());
        assertEquals(permissionDescription, oldPermission.getDescription());
        assertNotEquals(newPermissionDescription, oldPermission.getDescription());

        repository.save(permission);

        final Permission newPermission = repository.findOne(permission.getName());
        assertNotEquals(permissionDescription, newPermission.getDescription());
        assertEquals(newPermissionDescription, newPermission.getDescription());
    }

    @Test
    public void Delete(){
        repository.save(permission);
        assertTrue(repository.exists(permission.getName()));

        repository.delete(permission);
        assertFalse(repository.exists(permission.getName()));
    }
}