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

import pl.edu.pw.elka.rso.eres3.domain.entities.Semester;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SemesterControllerIntegrationTest {
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
    
    private static final String mapping = "/semesters";
    
    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into semester(id, type, year) values(1, \"WINTER\", 1918), (2, \"SUMMER\", 1918);";
        stmt.executeUpdate(sql);
        conn.close();
        
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        
        String sql = "delete from semester where id<3;"; 
        stmt.executeUpdate(sql);
        conn.close();
    }

    @Test
    public void getAllSemestersTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Semester[] semesters = objectMapper.readValue(results, Semester[].class);
    	long id = 0;
    	for(Semester semester: semesters){
    		if(semester.getType().equals(Semester.SemesterType.SUMMER)){
    			id = semester.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Semester not found!");
    	}
    }
    
    @Test
    public void getSemesterTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping + "/1"))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Semester semester = objectMapper.readValue(results, Semester.class);
    	if(!semester.getType().equals(Semester.SemesterType.WINTER)){
    		fail("Wrong semester found!");
    	}
    }
 
}
