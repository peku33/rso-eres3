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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationalUnitControllerIntegrationTest {
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
    
    private static final String mapping = "/units";

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"Test\", \"Test\"), (2, \"Test2\", \"Test2\");";
        stmt.executeUpdate(sql);
        conn.close();
       
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "delete from organizational_unit where id<3 or short_name='Test3';";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addUnitTest() throws Exception{
    	OrganizationalUnitDto unitStub = new OrganizationalUnitDto();
    	unitStub.setFullName("Test3");
    	unitStub.setShortName("Test3");
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(unitStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void updateUnitTest() throws Exception{
    	String name = "Testo";
    	OrganizationalUnitDto unitStub = new OrganizationalUnitDto();
    	unitStub.setFullName("Test2");
    	unitStub.setShortName(name);
    	unitStub.setId((short) 2);
    	
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(unitStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    	
    	String result = this.mockMvc.perform(get(mapping+"/2")).andExpect(status().is2xxSuccessful())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andReturn().getResponse().getContentAsString();
    	
    	OrganizationalUnitDto upUnit = objectMapper.readValue(result, OrganizationalUnitDto.class);
    	if(!upUnit.getShortName().equals(name)){
    		fail("Update failed!");
    	}
    }
    
    @Test
    public void getAllUnitsTest() throws Exception{
    	String results = this.mockMvc.perform(get(mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(jsonPath("$[?(@.fullName=='Test2')]").isNotEmpty())
    			.andReturn().getResponse().getContentAsString()
    			;
    	OrganizationalUnitDto[] units = objectMapper.readValue(results, OrganizationalUnitDto[].class);
    	long id = 0;
    	for(OrganizationalUnitDto unit: units){
    		if(unit.getFullName().equals("Test2")){
    			id = unit.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Unit not found!");
    	}
    }
    
    @Test
    public void deleteUnitTest() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	OrganizationalUnitDto[] units = objectMapper.readValue(results, OrganizationalUnitDto[].class);
    	for(OrganizationalUnitDto unit: units){
    		if(unit.getShortName().equals("Test")){
    			fail("Unit found after delete!");
    		}
    	}
    		
    }
}
