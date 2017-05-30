package pl.edu.pw.elka.rso.eres3.security.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.rso.eres3.domain.compositeids.GrantedPermissionId;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;
import pl.edu.pw.elka.rso.eres3.security.AuthenticatedUser;
import pl.edu.pw.elka.rso.eres3.security.SecuritySettings;
import pl.edu.pw.elka.rso.eres3.security.domain.recognizers.EntityUnitRecognizer;
import pl.edu.pw.elka.rso.eres3.security.domain.recognizers.EntityUnitRecognizerCollection;

import java.io.Serializable;

@Component
public class DomainPermissionEvaluator implements PermissionEvaluator {
	private static final Logger logger = LoggerFactory.getLogger(DomainPermissionEvaluator.class);

	private final GrantedPermissionRepository repository;
	private final EntityUnitRecognizerCollection entityUnitRecognizers;
	private final boolean checkPermissions;

	@Autowired
	public DomainPermissionEvaluator(final GrantedPermissionRepository repository,
                                     final EntityUnitRecognizerCollection entityUnitRecognizers,
                                     final SecuritySettings securitySettings){
	    this.repository = repository;
	    this.entityUnitRecognizers = entityUnitRecognizers;
	    this.checkPermissions = securitySettings.isCheckPermissions();
    }

	private boolean hasPermissionOnUnit(final Authentication authentication, final Short organizationalUnitId, final String permissionName)
	{
		final Long userId = getUserId(authentication);
		logger.info(String.format("Checking uid %s permission to orgUnitId %s for %s operation", userId, organizationalUnitId, permissionName));
		final GrantedPermission grantedPermission = repository.findOne(new GrantedPermissionId(
				userId, permissionName, organizationalUnitId
		));
		if(grantedPermission == null){
			logger.info(String.format("%s access to orgUnitId %s forbidden", permissionName, organizationalUnitId));
			return false;
		}
		logger.info(String.format("Permission %s to orgUnitId %s granted", permissionName, organizationalUnitId));
		return true;
	}

	private Long getUserId(Authentication authentication) {
		final AuthenticatedUser user = (AuthenticatedUser)authentication.getPrincipal();
		return user.getId();
	}

	private boolean hasPermissionOnAnyUnit(final Authentication authentication, final String permissionName)
	{
		final Long userId = getUserId(authentication);
		logger.info(String.format("Checking uid %s permission on any unit for %s operation", userId, permissionName));
		final GrantedPermission grantedPermission =
                repository.getFirstByPersonIdAndPermissionNameOrderByUnitId(userId, permissionName);
		if(grantedPermission == null){
            logger.info(String.format("%s access on all units forbidden", permissionName));
            return false;
        }
        logger.info(String.format("Permission %s to orgUnitId %s granted", permissionName, grantedPermission.getUnit().getId()));
        return true;
	}

	@Override
	public boolean hasPermission(final Authentication authentication,
			final Object targetDomainObject, final Object permissionName)
	{
		if(!checkPermissions) {
			return true;
		}
		if(targetDomainObject == null) {
			return hasPermissionOnAnyUnit(authentication, (String)permissionName);
		}
		final Class<?> classToRecognize = targetDomainObject.getClass();
		final EntityUnitRecognizer unitRecognizer = entityUnitRecognizers.getByClass(classToRecognize);
		@SuppressWarnings("unchecked")
		final Short unitId = unitRecognizer.getUnitIdByEntity(targetDomainObject);
		return hasPermissionOnUnit(authentication, unitId, (String)permissionName);
	}

	@Override
	public boolean hasPermission(final Authentication authentication,
			final Serializable targetId, final String targetType, final Object permissionName)
	{
		if(!checkPermissions) {
			return true;
		}
		if(targetId == null) {
			return hasPermissionOnAnyUnit(authentication, (String)permissionName);
		}
		final EntityUnitRecognizer unitRecognizer = entityUnitRecognizers.getByClassName(targetType);
        @SuppressWarnings("unchecked")
		final Short unitId = unitRecognizer.getUnitIdByEntityId(targetId);
		return hasPermissionOnUnit(authentication, unitId, (String)permissionName);
	}
}
