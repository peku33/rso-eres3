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

import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubjectControllerIntegrationTest {
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
    
    private static final String mapping = "/subjects";
    
    private static OrganizationalUnit unitStub = new OrganizationalUnit();

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"test\", \"test\")";
        stmt.executeUpdate(sql);
        sql = "insert into subject(id, didactical_units, ects, full_name, short_name, type, unit_id) values"
        		+ "(1, 3, 4, \"Test\", \"Test\", \"EXAM\", 1), "
        		+ "(2, 3, 4, \"Test2\", \"Test2\", \"EXAM\", 1); ";
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
        String sql = "delete from subject where id<3 or short_name='Test3';";
        stmt.executeUpdate(sql);
        sql = "delete from organizational_unit where id=1;";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addSubjectTest() throws Exception{
    	Subject subjectStub = new Subject();
    	subjectStub.setFullName("Test3");
    	subjectStub.setShortName("Test3");
    	subjectStub.setEcts((byte)4);
    	subjectStub.setDidacticalUnits((byte) 3);
    	subjectStub.setType(Subject.SubjectType.EXAM);
    	subjectStub.setUnit(unitStub);
    	
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(subjectStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void updateSubjectTest() throws Exception{
    	String name = "Testo";
    	Subject subjectStub = new Subject();
    	subjectStub.setFullName("Test2");
    	subjectStub.setShortName(name);
    	subjectStub.setEcts((byte)4);
    	subjectStub.setDidacticalUnits((byte) 3);
    	subjectStub.setType(Subject.SubjectType.EXAM);
    	subjectStub.setUnit(unitStub);
    	subjectStub.setId(2);
    	
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(subjectStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    	
    	String result = this.mockMvc.perform(get(mapping+"/2")).andExpect(status().is2xxSuccessful())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andReturn().getResponse().getContentAsString();
    	
    	Subject upSubject = objectMapper.readValue(result, Subject.class);
    	if(!upSubject.getShortName().equals(name)){
    		fail("Update failed!");
    	}
    }
    
    @Test
    public void getAllSubjectsTest() throws Exception{
    	String results = this.mockMvc.perform(get(OrganizationalUnitController.mapping + "/1" + mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(jsonPath("$[?(@.fullName=='Test2')]").isNotEmpty())
    			.andReturn().getResponse().getContentAsString()
    			;
    	Subject[] subjects = objectMapper.readValue(results, Subject[].class);
    	long id = 0;
    	for(Subject subject: subjects){
    		if(subject.getFullName().equals("Test2")){
    			id = subject.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Subject not found!");
    	}
    }
    
    @Test
    public void deleteSubjectTest() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(OrganizationalUnitController.mapping+"/1" + mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Subject[] subjects = objectMapper.readValue(results, Subject[].class);
    	for(Subject subject: subjects){
    		if(subject.getShortName().equals("Test")){
    			fail("Subject found after delete!");
    		}
    	}
    		
    }
}
