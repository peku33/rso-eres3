package pl.edu.pw.elka.rso.eres3.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

@Service("userDetailsService")
public class RestUserDetailsService implements UserDetailsService {
	@Autowired
	private PersonRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person person = repository.findByLogin(username);
		if(person == null)
			throw new UsernameNotFoundException(String.format("User {} not found", username));
		
		List<GrantedAuthority> authorities = buildUserAuthority(person);
		return buildUserForAuthentication(person, authorities);
	}
	
	private User buildUserForAuthentication(Person user, List<GrantedAuthority> authorities) {
		return new AuthenticatedUser(
					    user.getId(),
				        user.getLogin(), 
						user.getPassword(),
						true, true, true, true,
						authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Person user) 
	{
		/**
		 * @todo: Put some code here if some roles are needed.
		 * Maybe it'll be useful to pick some ADMINs and USERs here, dk
		 */
		return new ArrayList<GrantedAuthority>();
	}
}
