package pl.edu.pw.elka.rso.eres3.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.entities.dto.PersonDto;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;
import pl.edu.pw.elka.rso.eres3.security.exceptions.RegistrationFailedException;

@Service
@Transactional
public class PersonService {
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Person registerNewPersonAccount(PersonDto personDto) throws RegistrationFailedException
	{
		if(personDto.getId() != null)
			throw new RegistrationFailedException("ID shouldn't be set on registration");
		if(loginExist(personDto.getLogin()))
			throw new RegistrationFailedException("Account with specified login exists yet");
		Person person = mapPersonFromDto(personDto);
		return repository.save(person);
	}
	
	public Person mapPersonFromDto(PersonDto personDto)
	{
		Person person = modelMapper.map(personDto, Person.class);
		person.setPassword(passwordEncoder.encode(personDto.getPassword()));
		return person;
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
