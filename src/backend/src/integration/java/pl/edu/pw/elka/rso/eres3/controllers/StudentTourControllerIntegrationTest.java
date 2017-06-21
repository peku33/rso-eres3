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

import pl.edu.pw.elka.rso.eres3.domain.entities.Person;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTour;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTourControllerIntegrationTest {
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
    
    private static final String mapping = "/studenttours";
    
    private static OrganizationalUnit unitStub = new OrganizationalUnit();
    private static Person personStub = new Person();

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"test\", \"test\")";
        stmt.executeUpdate(sql);
        sql = "insert into person(login, password, id, unit_id) values(\"Test\", \"Test\", 1,1);";
        stmt.executeUpdate(sql);
        sql = "insert into student_tour(id, album_no, person_id, unit_id) values(1, 'Test', 1, 1),"
        		+ "(2, 'Test2', 1, 1), (3, 'Test3', 1, 1);";
        stmt.executeUpdate(sql);
        conn.close();
        
    	unitStub.setFullName("test");
    	unitStub.setShortName("test");
    	unitStub.setId((short) 1);
    	personStub.setUnit(unitStub);
    	personStub.setLogin("Test");
    	personStub.setPassword("Test");
    	personStub.setId(new Long(1));
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "delete from student_tour where id<4 or album_no='Test4'";
        stmt.executeUpdate(sql);
        sql = "delete from person where id=1;";
        stmt.executeUpdate(sql);
        sql = "delete from organizational_unit where id=1;";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addStudentTourTest() throws Exception{
    	StudentTour tourStub = new StudentTour();
    	tourStub.setAlbumNo("Test4");
    	tourStub.setPerson(personStub);
    	tourStub.setUnit(unitStub);
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(tourStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void updatePersonTest() throws Exception{
    	String name = "Testo";
    	StudentTour tourStub = new StudentTour();
    	tourStub.setAlbumNo(name);
    	tourStub.setPerson(personStub);
    	tourStub.setUnit(unitStub);
    	tourStub.setId(new Long(2));
    	
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(tourStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    	
    	String result = this.mockMvc.perform(get(mapping+"/2")).andExpect(status().is2xxSuccessful())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andReturn().getResponse().getContentAsString();
    	
    	StudentTour upTour = objectMapper.readValue(result, StudentTour.class);
    	if(!upTour.getAlbumNo().equals(name)){
    		fail("Update failed!");
    	}
    }
    
    @Test
    public void getStudentTourTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping + "/1"))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	StudentTour result = objectMapper.readValue(results, StudentTour.class);
    	if(!result.getAlbumNo().equals("Test")){
    		fail("Fetch failed!");
    	}
    }
    
    @Test
    public void getAllStudentToursByPersonIdTest() throws Exception{
    	String results = this.mockMvc.perform(get(PersonController.mapping + "/1" + mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	StudentTour[] tours = objectMapper.readValue(results, StudentTour[].class);
    	Long id = new Long(0);
    	for(StudentTour tour: tours){
    		if(tour.getAlbumNo().equals("Test3")){
    			id = tour.getId();
    		}
    	}
    	if(id==0){
    		fail("Tour not found!");
    	}
    }
    
    @Test
    public void deleteStudentTourTest() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(PersonController.mapping + "/1" + mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	StudentTour[] tours = objectMapper.readValue(results, StudentTour[].class);
    	Long id = new Long(0);
    	for(StudentTour tour: tours){
    		if(tour.getAlbumNo().equals("Test1")){
    			fail("Delete failed!");
    		}
    	}
    		
    }
}
