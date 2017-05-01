package pl.edu.pw.elka.rso.eres3.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.GrantedPermissionRepository;

public class DomainPermissionEvaluator implements PermissionEvaluator {
	@Autowired
	private GrantedPermissionRepository repository;
	
	private boolean checkPermission(long userId, 
									OrganizationalUnit domain,
									String permissionName)
	{
		/**
		 * @todo: It should be replaced with some fancy WHERE clause.
		 * Iterating manually over a list isn't sexy.
		 */
		for(GrantedPermission grantedPermission: repository.getGrantedPermissionsByPersonId(userId))
		{
			if(grantedPermission.getUnit() == domain &&
			   grantedPermission.getPermission().getName() == permissionName)
				return true;
		}
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
    		Serializable targetId, String targetType, Object permissionText) 
    {
		/**
		 * I don't think it'll be needed
		 */
		throw new UnsupportedOperationException("Domain selection by id isn't supported");
    }
}
