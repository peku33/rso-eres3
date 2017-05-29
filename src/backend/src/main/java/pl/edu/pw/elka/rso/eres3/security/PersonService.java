package pl.edu.pw.elka.rso.eres3.security;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.entities.dto.PersonDto;
import pl.edu.pw.elka.rso.eres3.domain.repositories.PersonRepository;
import pl.edu.pw.elka.rso.eres3.security.exceptions.PersonServiceException;

@Service
@Transactional
public class PersonService {
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Person registerNewPersonAccount(PersonDto personDto) throws PersonServiceException
	{
		if(personDto.getId() != null)
			throw new PersonServiceException("ID shouldn't be set on registration");
		if(loginExist(personDto.getLogin()))
			throw new PersonServiceException("Account with specified login exists yet");
		Person person = mapPersonFromDto(personDto);
		return repository.save(person);
	}
	
	public Person editPersonAccount(PersonDto personDto)
	{
		personDto.setLogin(null); // login setup not allowed
		Person person = repository.findOne(personDto.getId());
		copyNonNullProperties(mapPersonFromDto(personDto), person);
		return repository.save(person);
	}
		
	public Person mapPersonFromDto(PersonDto personDto)
	{
		Person person = modelMapper.map(personDto, Person.class);
		if(personDto.getPassword() != null)
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
	
	public static void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
}
