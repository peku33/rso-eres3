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

import pl.edu.pw.elka.rso.eres3.domain.entities.Permission;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermissionControllerIntegrationTest {
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
    
    private static final String mapping = "/permissions";
    

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into permission(name, description) values('Test', 'Test'), ('Test2', 'Test2');";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "delete from permission where name like 'Test%';";
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addPermission() throws Exception{
    	Permission permStub = new Permission();
    	permStub.setDescription("Test3");
    	permStub.setName("Test3");
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(permStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void updatePermission() throws Exception{
    	String name = "Testo";
    	Permission permStub = new Permission();
    	permStub.setName("Test2");
    	permStub.setDescription(name);
    	
    	this.mockMvc.perform(put(mapping).contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(permStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    	
    	String result = this.mockMvc.perform(get(mapping+"/Test2")).andExpect(status().is2xxSuccessful())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andReturn().getResponse().getContentAsString();
    	
    	Permission upPerm = objectMapper.readValue(result, Permission.class);
    	if(!upPerm.getDescription().equals(name)){
    		fail("Update failed!");
    	}
    }
    
    @Test
    public void getAllPermissionsTest() throws Exception{
    	this.mockMvc.perform(get(mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(jsonPath("$[?(@.name=='Test2')]").isNotEmpty())
    			;
    }
    
    @Test
    public void deletePermissionTest() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/Test")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	Permission[] perms = objectMapper.readValue(results, Permission[].class);
    	for(Permission perm: perms){
    		if(perm.getName().equals("Test")){
    			fail("Permission found after delete!");
    		}
    	}
    		
    }
}
