package pl.edu.pw.elka.rso.eres3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

@Service
public class JpaBasedUserDetailsService implements UserDetailsService {

    private PersonRepository personRepository;

    @Autowired
    JpaBasedUserDetailsService(PersonRepository PersonRepository){
        super();
        this.personRepository = PersonRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return personRepository.getPersonByLogin(login);
    }
}
