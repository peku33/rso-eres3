package pl.edu.pw.elka.rso.eres3.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;
import pl.edu.pw.elka.rso.eres3.domain.repositories.OrganizationalUnitRepository;

public class DomainPermissionEvaluator implements PermissionEvaluator {
	private static final Logger logger = LoggerFactory.getLogger(DomainPermissionEvaluator.class);

	@Autowired
	private GrantedPermissionRepository repository;

	@Autowired
	private OrganizationalUnitRepository domainRepository;

	private boolean checkPermission(long userId, 
			OrganizationalUnit domain,
			String permissionName)
	{
		logger.info(String.format("Checking uid %s permission to %s for %s operation", userId, domain.getShortName(), permissionName));
		/**
		 * @todo: It should be replaced with some fancy WHERE clause.
		 * Iterating manually over a list isn't sexy.
		 * Remember to use getGrantedPermissionsByPersonId, when it will be implemented
		 */		
		for(GrantedPermission grantedPermission: repository.findAll())
		{
			if(grantedPermission.getPerson().getId() == userId &&
					grantedPermission.getUnit().equals(domain) &&
					grantedPermission.getPermission().getName().equals(permissionName))
			{
				logger.info(String.format("Permission %s to %s granted", permissionName, domain.getShortName()));
				return true;
			}
		}
		logger.info(String.format("%s access to %s forbidden", permissionName, domain.getShortName()));
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, 
			Object targetDomainObject, Object permissionName) 
	{
		AuthenticatedUser user = (AuthenticatedUser)authentication.getPrincipal();
		OrganizationalUnit domain = (OrganizationalUnit)targetDomainObject;
		return checkPermission(user.getId(), domain, (String)permissionName);
	}

	@Override
	public boolean hasPermission(Authentication authentication, 
			Serializable targetId, String targetType, Object permissionName) 
	{
		assert(targetType.equals("OrganizationalUnit"));
		AuthenticatedUser user = (AuthenticatedUser)authentication.getPrincipal();
		OrganizationalUnit domain = domainRepository.findOne((Short)targetId);
		return checkPermission(user.getId(), domain, (String)permissionName);
	}
}
