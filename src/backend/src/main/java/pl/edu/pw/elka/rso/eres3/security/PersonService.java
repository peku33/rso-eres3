package pl.edu.pw.elka.rso.eres3.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.entities.dto.PersonDto;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;

@Service
@Transactional
public class PersonService {
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Person registerNewPersonAccount(PersonDto personDto)
	{
		if(loginExist(personDto.getLogin()))
		{
			// @todo: throw exception
		}
		
		Person person = modelMapper.map(personDto, Person.class);
		person.setPassword(passwordEncoder.encode(personDto.getPassword()));
		return repository.save(person);
	}
	
	public PersonDto mapPersonToDto(Person person)
	{
		PersonDto personDto = modelMapper.map(person, PersonDto.class);
		personDto.setPassword(null);
		return personDto;
	}
		
	private boolean loginExist(String login)
	{
		return repository.findByLogin(login) != null;
	}
}
