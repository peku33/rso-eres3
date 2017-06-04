package pl.edu.pw.elka.rso.eres3.controllers;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.edu.pw.elka.rso.eres3.domain.entities.dto.OrganizationalUnitDto;
import pl.edu.pw.elka.rso.eres3.domain.entities.dto.PersonDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {
	@Autowired
    private WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;
	
    private MockMvc mockMvc;
    
    static final String mapping = "/persons";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }
    
    @Test
    public void addPerson() throws Exception{
    	Random rand = new Random();
    	String rstring = "test" + new Integer((rand.nextInt())).toString();
    	PersonDto personStub = new PersonDto();
    	OrganizationalUnitDto unitStub = new OrganizationalUnitDto();
    	unitStub.setFullName("test");
    	unitStub.setShortName("test");
    	unitStub.setId((short) 1);
    	personStub.setUnit(unitStub);
    	personStub.setLogin(rstring);
    	personStub.setPassword("test");
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(personStub))
    			)
    	.andExpect(status().isOk());
    }
    
    @Test
    public void insertUpdateDelete() throws Exception{
    	Random rand = new Random();
    	String rstring = "test" + new Integer((rand.nextInt())).toString();
    	PersonDto personStub = new PersonDto();
    	OrganizationalUnitDto unitStub = new OrganizationalUnitDto();
    	unitStub.setFullName("test");
    	unitStub.setShortName("test");
    	unitStub.setId((short) 1);
    	personStub.setUnit(unitStub);
    	personStub.setLogin(rstring);
    	personStub.setPassword("test");
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(personStub))
    			)
    	.andExpect(status().isOk());
    	
    	String results = this.mockMvc.perform(get(mapping))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(jsonPath("$[?(@.login=='"+rstring+"')]").isNotEmpty())
    			.andReturn().getResponse().getContentAsString()
    			;
    	PersonDto[] persons = objectMapper.readValue(results, PersonDto[].class);
    	long id = 0;
    	for(PersonDto person: persons){
    		if(person.getLogin().equals(rstring)){
    			id = person.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Person not found!");
    	}
    	
    	rstring = "test" + new Integer((rand.nextInt())).toString();
    	personStub.setId(id);
    	personStub.setFirstName(rstring);
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(personStub))
    			)
    	.andExpect(status().isOk());
    	
    	String result = this.mockMvc.perform(get(mapping+"/"+new Long(id).toString())).andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andReturn().getResponse().getContentAsString();
    	
    	PersonDto upPerson = objectMapper.readValue(result, PersonDto.class);
    	if(!upPerson.getFirstName().equals(rstring)){
    		fail("Update failed!");
    	}
    	this.mockMvc.perform(delete(mapping+"/"+new Long(id).toString())).andExpect(status().isNoContent());
    	
    }
}
