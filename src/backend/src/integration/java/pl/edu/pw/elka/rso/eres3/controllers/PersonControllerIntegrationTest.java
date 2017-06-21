package pl.edu.pw.elka.rso.eres3.controllers;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

import java.sql.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class PersonControllerIntegrationTest {
	@Autowired
    private WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	@Value("${spring.datasource.username}")
	private String dbUser;
	
	@Value("${spring.datasource.password}")
	private String dbPass;
	
    private MockMvc mockMvc;
    
    private static final String mapping = "/persons";
    
    private static OrganizationalUnitDto unitStub = new OrganizationalUnitDto();

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"test\", \"test\")";
        stmt.executeUpdate(sql);
        sql = "insert into person(login, password, id, unit_id) values(\"Test\", \"Test\", 1,1), (\"Test2\", \"Test\", 2, 1);";
        stmt.executeUpdate(sql);
        conn.close();
        
    	unitStub.setFullName("test");
    	unitStub.setShortName("test");
    	unitStub.setId((short) 1);
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "delete from person where id<4 or login='Test3';";
        stmt.executeUpdate(sql);
        sql = "delete from organizational_unit where id=1;";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addPersonTest() throws Exception{
    	PersonDto personStub = new PersonDto();

    	personStub.setUnit(unitStub);
    	personStub.setLogin("Test3");
    	personStub.setPassword("Test");
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(personStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void updatePersonTest() throws Exception{
    	String name = "Testo";
    	PersonDto personStub = new PersonDto();
    	personStub.setUnit(unitStub);
    	personStub.setLogin("Test2");
    	personStub.setPassword("Test");
    	personStub.setFirstName(name);
    	personStub.setId(new Long(2));
    	
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(personStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    	
    	String result = this.mockMvc.perform(get(mapping+"/2")).andExpect(status().is2xxSuccessful())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andReturn().getResponse().getContentAsString();
    	
    	PersonDto upPerson = objectMapper.readValue(result, PersonDto.class);
    	if(!upPerson.getFirstName().equals(name)){
    		fail("Update failed!");
    	}
    }
    
    @Test
    public void getAllStudentsTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(jsonPath("$[?(@.login=='Test2')]").isNotEmpty())
    			.andReturn().getResponse().getContentAsString()
    			;
    	PersonDto[] persons = objectMapper.readValue(results, PersonDto[].class);
    	long id = 0;
    	for(PersonDto person: persons){
    		if(person.getLogin().equals("Test2")){
    			id = person.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Person not found!");
    	}
    }
    
    @Test
    public void deleteStudentTest() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	PersonDto[] persons = objectMapper.readValue(results, PersonDto[].class);
    	for(PersonDto person: persons){
    		if(person.getLogin().equals("Test")){
    			fail("Person found after delete!");
    		}
    	}
    		
    }
}
