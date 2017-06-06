package pl.edu.pw.elka.rso.eres3.controllers;

import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

import pl.edu.pw.elka.rso.eres3.domain.entities.Semester;
import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTour;
import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTourSemester;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTourSemesterControllerIntegrationTest {
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
    
    private static final String mapping = "/studenttoursemesters";

    private static StudentTour tourStub = new StudentTour();
    private static Semester semStub = new Semester();
    private static Specialization specStub = new Specialization();

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
        sql = "insert into semester(id, type, year) values(1, \"WINTER\", 1918), (2, 'SUMMER', 1918), (3, 'WINTER', 1919),"
        		+ "(4, 'SUMMER', 1919);";
        stmt.executeUpdate(sql);
        sql = "insert into specialization(id, full_name, short_name, type, unit_id) values(1, \"Test\", \"Test\", 'MAJOR', 1);";
        stmt.executeUpdate(sql);
        sql = "insert into student_tour_semester(id, nominal_semester_no, semester_id, specialization_id, student_tour_id)"
        		+ "values(1, 1, 1, 1, 1), (2, 2, 2, 1, 1), (3, 3, 3, 1, 1);";
        stmt.executeUpdate(sql);
        conn.close();
        
    	tourStub.setId(new Long(1));
    	tourStub.setAlbumNo("Test");
    	semStub.setId((short)4);
    	specStub.setId(1);
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "delete from student_tour_semester where id<4 or semester_id<5;";
        stmt.executeUpdate(sql);
        sql = "delete from specialization where id=1;";
        stmt.executeUpdate(sql);
        sql = "delete from semester where id<5;"; 
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
   public void addTourSemesterTest() throws Exception{
   	StudentTourSemester tsemStub = new StudentTourSemester();
   	tsemStub.setNominalSemesterNo((byte)4);
   	tsemStub.setSemester(semStub);
   	tsemStub.setSpecialization(specStub);
   	tsemStub.setTour(tourStub);
   	
   	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
   			.content(objectMapper.writeValueAsString(tsemStub))
   			)
   	.andExpect(status().is2xxSuccessful());
   	
   	String results = this.mockMvc.perform(get(StudentTourController.mapping + "/1" + mapping))
   			.andExpect(status().is2xxSuccessful())
   			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
   			.andReturn().getResponse().getContentAsString()
   			;
   	StudentTourSemester[] tsems = objectMapper.readValue(results, StudentTourSemester[].class);
   	long id = 0;
   	for(StudentTourSemester tsem: tsems){
   		if(tsem.getSemester().getId().equals((short) 4)){
   			id = tsem.getId();
   			break;
   		}
   	}
   	if(id==0){
   		fail("Tour semester not found!");
   	}
   	
   }
   
   @Test
   public void updateTourSemesterTest() throws Exception{
   	StudentTourSemester tsemStub = new StudentTourSemester();
   	tsemStub.setNominalSemesterNo((byte)4);
   	tsemStub.setSemester(semStub);
   	tsemStub.setSpecialization(specStub);
   	tsemStub.setTour(tourStub);
   	tsemStub.setId(new Long(3));
   	
   	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON)
   			.content(objectMapper.writeValueAsString(tsemStub))
   			)
   	.andExpect(status().is2xxSuccessful());
   	
   	String results = this.mockMvc.perform(get(StudentTourController.mapping + "/1" + mapping))
   			.andExpect(status().is2xxSuccessful())
   			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
   			.andReturn().getResponse().getContentAsString()
   			;
   	StudentTourSemester[] tsems = objectMapper.readValue(results, StudentTourSemester[].class);
   	long id = 0;
   	for(StudentTourSemester tsem: tsems){
   		if(tsem.getSemester().getId().equals((short) 4) && tsem.getId().equals(new Long(3))){
   			id = tsem.getId();
   			break;
   		}
   	}
   	if(id==0){
   		fail("Tour semester not found!");
   	}
   	
   }
   
   @Test
   public void deleteTourSemesterTest() throws Exception{
   	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
   	
   	String results = this.mockMvc.perform(get(StudentTourController.mapping + "/1" + mapping))
   			.andExpect(status().is2xxSuccessful())
   			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
   			.andReturn().getResponse().getContentAsString()
   			;
   	StudentTourSemester[] tsems = objectMapper.readValue(results, StudentTourSemester[].class);
   	for(StudentTourSemester tsem: tsems){
   		if(tsem.getId().equals(new Long(1))){
   			fail("Tour semester found after delete!");
   		}
   	}
   	}
   
   @Test
   public void getTourSemestersByTourIdTest() throws Exception{
   	String results = this.mockMvc.perform(get(StudentTourController.mapping + "/1" + mapping))
   			.andExpect(status().is2xxSuccessful())
   			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
   			.andReturn().getResponse().getContentAsString()
   			;
   	StudentTourSemester[] tsems = objectMapper.readValue(results, StudentTourSemester[].class);
   	long id = 0;
   	for(StudentTourSemester tsem: tsems){
   		if(tsem.getId().equals(new Long(2))){
   			id = tsem.getId();
   			break;
   		}
   	}
   	if(id==0){
   		fail("Tour semester not found!");
   	}
   }
   
   @Test
   public void getTourSemesterTest() throws Exception{
	String results = this.mockMvc.perform(get(mapping +"/1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andReturn().getResponse().getContentAsString()
			;
	StudentTourSemester tsem = objectMapper.readValue(results, StudentTourSemester.class);
	if(!tsem.getId().equals(new Long(1))){
		fail("Tour semester not found!");
	}
   }
}
