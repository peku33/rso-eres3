package pl.edu.pw.elka.rso.eres3.controllers;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    	.andExpect(status().isCreated());
    }
}
