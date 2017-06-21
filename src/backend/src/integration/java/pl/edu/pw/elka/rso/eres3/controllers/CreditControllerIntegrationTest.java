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

import pl.edu.pw.elka.rso.eres3.domain.entities.Credit;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTour;
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectRealization;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditControllerIntegrationTest {
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
    
    private static final String mapping = "/credits";

    private static SubjectRealization realStub = new SubjectRealization();
    private static StudentTour tourStub = new StudentTour();

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"test\", \"test\")";
        stmt.executeUpdate(sql);
        sql = "insert into person(login, password, id, unit_id) values(\"Test\", \"Test\", 1,1);";
        stmt.executeUpdate(sql);
        sql = "insert into student_tour(id, album_no, person_id, unit_id) values(1, 'Test', 1, 1);";
        stmt.executeUpdate(sql);
        sql = "insert into subject(id, didactical_units, ects, full_name, short_name, type, unit_id) values"
        		+ "(1, 3, 4, \"Test\", \"Test\", \"EXAM\", 1);";
        stmt.executeUpdate(sql);
        sql = "insert into subject_version(id, version_code, subject_id) values(1, '1', 1);";
        stmt.executeUpdate(sql);
        sql = "insert into semester(id, type, year) values(1, \"WINTER\", 1918);";
        stmt.executeUpdate(sql);
        sql = "insert into subject_realization(id, semester_id, subject_version_id) values(1, 1, 1);";
        stmt.executeUpdate(sql);
        sql = "insert into credit(id, realization_id, tour_id) values(1, 1, 1), (2, 1, 1);";
        stmt.executeUpdate(sql);
        conn.close();
        
    	realStub.setId(1);
    	tourStub.setId(new Long(1));
    	tourStub.setAlbumNo("Test");
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "delete from credit where id<3 or realization_id=1;";
        stmt.executeUpdate(sql);			
        sql = "delete from subject_realization where semester_id=1;"; 
        stmt.executeUpdate(sql);		
        sql = "delete from subject_version where id=1;"; 
        stmt.executeUpdate(sql);
        sql = "delete from subject where id=1;";
        stmt.executeUpdate(sql);
        sql = "delete from semester where id=1;"; 
        stmt.executeUpdate(sql);
        sql = "delete from student_tour where id=1";
        stmt.executeUpdate(sql);
        sql = "delete from person where id=1;";
        stmt.executeUpdate(sql);
        sql = "delete from organizational_unit where id=1;";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addCreditTest() throws Exception{
    	Credit creditStub = new Credit();
    	creditStub.setRealization(realStub);
    	creditStub.setTour(tourStub);
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(creditStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void updateCreditTest() throws Exception{
    	Credit creditStub = new Credit();
    	creditStub.setRealization(realStub);
    	creditStub.setTour(tourStub);
    	creditStub.setId(new Long(2));
    	
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(creditStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void getAllCreditsTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Credit[] credits = objectMapper.readValue(results, Credit[].class);
    	long id = 0;
    	for(Credit credit: credits){
    		if(credit.getId().equals(new Long(2))){
    			id = credit.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Credit not found!");
    	}
    }
    
    @Test
    public void getCreditsByTourTest() throws Exception{
    	String results = this.mockMvc.perform(get(StudentTourController.mapping + "/1" + mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Credit[] credits = objectMapper.readValue(results, Credit[].class);
    	long id = 0;
    	for(Credit credit: credits){
    		if(credit.getId().equals(new Long(2))){
    			id = credit.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Credit not found!");
    	}
    }
    
    @Test
    public void getCreditsByRealizationTest() throws Exception{
    	String results = this.mockMvc.perform(get(SubjectRealizationController.mapping + "/1" + mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Credit[] credits = objectMapper.readValue(results, Credit[].class);
    	long id = 0;
    	for(Credit credit: credits){
    		if(credit.getId().equals(new Long(2))){
    			id = credit.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Credit not found!");
    	}
    }
    
    @Test
    public void getCreditTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping+"/2"))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Credit credit = objectMapper.readValue(results, Credit.class);
    	if(!credit.getId().equals(new Long(2))){
    		fail("Credit not found!");
    	}
    }
    
    @Test
    public void deleteCreditTest() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Credit[] credits = objectMapper.readValue(results, Credit[].class);
    	for(Credit credit: credits){
    		if(credit.getId().equals(new Long(1))){
    			fail("Credit found after delete!");
    		}
    	}
    		
    }
}
