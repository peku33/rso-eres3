package pl.edu.pw.elka.rso.eres3.security.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import pl.edu.pw.elka.rso.eres3.domain.compositeids.GrantedPermissionId;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;
import pl.edu.pw.elka.rso.eres3.security.AuthenticatedUser;
import pl.edu.pw.elka.rso.eres3.security.SecuritySettings;
import pl.edu.pw.elka.rso.eres3.security.domain.recognizers.EntityUnitRecognizer;
import pl.edu.pw.elka.rso.eres3.security.domain.recognizers.EntityUnitRecognizerCollection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jakub Lorenc on 20.05.17.
 */
public class DomainPermissionEvaluatorTest {
    private static final Long userId = 1L;
    private static final String permission1 = "permission1";
    private static final String permission2 = "permission2";
    private static final Short unitId = (short)1;
    private static final GrantedPermissionId permissionId_1 = new GrantedPermissionId(userId, permission1, unitId);
    private static final GrantedPermissionId permissionId_2 = new GrantedPermissionId(userId, permission2, unitId);

    private DomainPermissionEvaluator evaluator;
    private GrantedPermissionRepository repository;
    private EntityUnitRecognizerCollection entityUnitRecognizers;
    private SecuritySettings securitySettings;
    private Authentication authentication;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(GrantedPermissionRepository.class);
        entityUnitRecognizers = Mockito.mock(EntityUnitRecognizerCollection.class);
        securitySettings = new SecuritySettings();

        authentication = Mockito.mock(Authentication.class);
        final AuthenticatedUser user = Mockito.mock(AuthenticatedUser.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);

        final EntityUnitRecognizer recognizer = Mockito.mock(EntityUnitRecognizer.class);
        Mockito.when(recognizer.getUnitIdByEntity(Mockito.any())).thenReturn(unitId);
        Mockito.when(recognizer.getUnitIdByEntityId(Mockito.any())).thenReturn(unitId);
        Mockito.when(entityUnitRecognizers.getByClass(Mockito.any())).thenReturn(recognizer);
        Mockito.when(entityUnitRecognizers.getByClassName(Mockito.anyString())).thenReturn(recognizer);
    }

    @Test
    public void withoutPermissionChecking() throws Exception {
        securitySettings.setCheckPermissions(false);
        constructEvaluator();

        assertTrue(evaluator.hasPermission(null, null, null));
        assertTrue(evaluator.hasPermission(null, null, null, null));
    }

    private void constructEvaluator(){
        evaluator = new DomainPermissionEvaluator(repository, entityUnitRecognizers, securitySettings);
    }

    @Test
    public void hasPermissionOnUnit() throws Exception {
        securitySettings.setCheckPermissions(true);
        constructEvaluator();

        final GrantedPermission permission = Mockito.mock(GrantedPermission.class);
        Mockito.when(repository.findOne(permissionId_1)).thenReturn(permission);
        Mockito.when(repository.findOne(permissionId_2)).thenReturn(null);

        assertTrue(evaluator.hasPermission(authentication, new Object(), permission1));
        assertTrue(evaluator.hasPermission(authentication, 0, "", permission1));
        assertFalse(evaluator.hasPermission(authentication, new Object(), permission2));
        assertFalse(evaluator.hasPermission(authentication, 0, "", permission2));
    }

    @Test
    public void hasPermissionOnAnyUnit() throws Exception {
        securitySettings.setCheckPermissions(true);
        constructEvaluator();

        final GrantedPermission permission = Mockito.mock(GrantedPermission.class);
        Mockito.when(repository.getFirstByPersonIdAndPermissionNameOrderByUnitId(userId, permission1)).thenReturn(permission);
        Mockito.when(repository.getFirstByPersonIdAndPermissionNameOrderByUnitId(userId, permission2)).thenReturn(null);

        final OrganizationalUnit unit = Mockito.mock(OrganizationalUnit.class);
        Mockito.when(permission.getUnit()).thenReturn(unit);
        Mockito.when(unit.getId()).thenReturn(unitId);

        assertTrue(evaluator.hasPermission(authentication, null, permission1));
        assertTrue(evaluator.hasPermission(authentication, null, null, permission1));
        assertFalse(evaluator.hasPermission(authentication, null, permission2));
        assertFalse(evaluator.hasPermission(authentication, null, null, permission2));
    }

}