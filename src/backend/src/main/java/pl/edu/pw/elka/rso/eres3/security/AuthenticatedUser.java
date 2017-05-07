package pl.edu.pw.elka.rso.eres3.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthenticatedUser extends User {
	private final long userId;

	public AuthenticatedUser(long userId,
							 String username, String password, boolean enabled, boolean accountNonExpired,
	                  		 boolean credentialsNonExpired,
	                  		 boolean accountNonLocked,
	                  		 Collection<? extends GrantedAuthority> authorities)
	{
	    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	    this.userId = userId;
	}
	
	public long getId() {
		return this.userId;
	}
}
