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

import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;
import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization.SpecializationType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecializationControllerIntegrationTest {
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
    
    private static final String mapping = "/specializations";
    
    private static OrganizationalUnit unitStub = new OrganizationalUnit();

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"test\", \"test\")";
        stmt.executeUpdate(sql);
        sql = "insert into specialization(id, full_name, short_name, type, unit_id) values(1, \"Test\", \"Test\", 'MAJOR', 1),"
        		+ "(2, 'Test2', 'Test2', 'MAJOR', 1);";
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
        String sql = "delete from specialization where id<3 or short_name='Test3';";
        stmt.executeUpdate(sql);
        sql = "delete from organizational_unit where id=1;";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addSpecializationTest() throws Exception{
    	Specialization specStub = new Specialization();
    	specStub.setFullName("Test3");
    	specStub.setShortName("Test3");
    	specStub.setType(SpecializationType.MAJOR);
    	specStub.setUnit(unitStub);
    	
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(specStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void updateSubjectTest() throws Exception{
    	String name = "Testo";
    	Specialization specStub = new Specialization();
    	specStub.setFullName("Test2");
    	specStub.setShortName(name);
    	specStub.setType(SpecializationType.MAJOR);
    	specStub.setUnit(unitStub);
    	specStub.setId(2);
    	
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(specStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    	
    	String result = this.mockMvc.perform(get(mapping+"/2")).andExpect(status().is2xxSuccessful())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andReturn().getResponse().getContentAsString();
    	
    	Specialization upSpec = objectMapper.readValue(result, Specialization.class);
    	if(!upSpec.getShortName().equals(name)){
    		fail("Update failed!");
    	}
    }
    
    @Test
    public void getSpecializationTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping+"/2"))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Specialization upSpec = objectMapper.readValue(results, Specialization.class);
    	if(!upSpec.getFullName().equals("Test2")){
    		fail("Specialization not found!");
    	}
    }
    
    @Test
    public void getSpecializationsOnUnitTest() throws Exception{
    	String results = this.mockMvc.perform(get(OrganizationalUnitController.mapping + "/1" + mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Specialization[] specs = objectMapper.readValue(results, Specialization[].class);
    	long id = 0;
    	for(Specialization spec: specs){
    		if(spec.getFullName().equals("Test2")){
    			id = spec.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Specialization not found!");
    	}
    }
    
    @Test
    public void deleteSpecializationTest() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(OrganizationalUnitController.mapping +"/1" + mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Specialization[] specs = objectMapper.readValue(results, Specialization[].class);
    	for(Specialization spec: specs){
    		if(spec.getShortName().equals("Test")){
    			fail("Specialization found after delete!");
    		}
    	}
    		
    }
}
