package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;
import pl.edu.pw.elka.rso.eres3.security.AuthenticatedUser;

@Component
public class DomainPermissionEvaluator implements PermissionEvaluator {
	private static final Logger logger = LoggerFactory.getLogger(DomainPermissionEvaluator.class);

	@Autowired private GrantedPermissionRepository repository;
	@Autowired private EntityUnitRecognizerCollection entityUnitRecognizers;

	private boolean checkPermissionOnUnit(final Authentication authentication, final Short organizationalUnitId, final String permissionName)
	{
		final AuthenticatedUser user = (AuthenticatedUser)authentication.getPrincipal();
		final long userId = user.getId();
		logger.info(String.format("Checking uid %s permission to orgUnitId %s for %s operation", userId, organizationalUnitId, permissionName));
		/**
		 * @todo: It should be replaced with some fancy WHERE clause.
		 * Iterating manually over a list isn't sexy.
		 * Remember to use getGrantedPermissionsByPersonId, when it will be implemented
		 */
		for(final GrantedPermission grantedPermission: repository.findAll())
		{
			if(grantedPermission.getPerson().getId() == userId &&
					grantedPermission.getUnit().getId().equals(organizationalUnitId) &&
					grantedPermission.getPermission().getName().equals(permissionName))
			{
				logger.info(String.format("Permission %s to orgUnitId %s granted", permissionName, organizationalUnitId));
				return true;
			}
		}
		logger.info(String.format("%s access to orgUnitId %s forbidden", permissionName, organizationalUnitId));
		return false;
	}

	@Override
	public boolean hasPermission(final Authentication authentication,
			final Object targetDomainObject, final Object permissionName)
	{
		final Class<?> classToRecognize = targetDomainObject.getClass();
		final EntityUnitRecognizer unitRecognizer = entityUnitRecognizers.getByClass(classToRecognize);
		final Short unitId = unitRecognizer.getUnitIdByEntity(targetDomainObject);
		return checkPermissionOnUnit(authentication, unitId, (String)permissionName);
	}

	@Override
	public boolean hasPermission(final Authentication authentication,
			final Serializable targetId, final String targetType, final Object permissionName)
	{
		final EntityUnitRecognizer unitRecognizer = entityUnitRecognizers.getByClassName(targetType);
		final Short unitId = unitRecognizer.getUnitIdByEntityId(targetId);
		return checkPermissionOnUnit(authentication, unitId, (String)permissionName);
	}
}
