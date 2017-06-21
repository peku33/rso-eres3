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

import pl.edu.pw.elka.rso.eres3.domain.compositeids.GrantedPermissionId;
import pl.edu.pw.elka.rso.eres3.domain.entities.GrantedPermission;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrantedPermissionControllerIntegrationTest {
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
    
    private static final String mapping = "permissions/granted";
    
    
    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"test\", \"test\")";
        stmt.executeUpdate(sql);
        sql = "insert into person(login, password, id, unit_id) values(\"Test\", \"Test\", 1,1),"
        		+ "(\"Test2\", \"Test2\", 2,1), (\"Test3\", \"Test3\", 3,1);";
        stmt.executeUpdate(sql);
        sql = "insert into permission(name, description) values('Test', 'Test');";
        stmt.executeUpdate(sql);
        sql = "insert into granted_permission(permission_name, person_id, unit_id) values('Test', 1, 1),"
        		+ "('Test',2, 1) ;";
        stmt.executeUpdate(sql);
        conn.close();

    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "delete from granted_permission where permission_name like 'Test%';";
        stmt.executeUpdate(sql);
        sql = "delete from permission where name like 'Test%';";
        stmt.executeUpdate(sql);
        sql = "delete from person where id<4;";
        stmt.executeUpdate(sql);
        sql = "delete from organizational_unit where id=1;";
        stmt.executeUpdate(sql);
        conn.close();
    }
    @Test
    public void addGrantedPermissionTest() throws Exception{
    	GrantedPermissionId granStub = new GrantedPermissionId();
    	granStub.setPermission("Test");
    	granStub.setUnit((short) 1);
    	granStub.setPerson(new Long(3));
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(granStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    
    @Test
    public void getAllGrantedPermissionsByPersonTest() throws Exception{
    	this.mockMvc.perform(get(mapping+"/person/2"))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(jsonPath("$[?(@.permissionName=='Test')]").isNotEmpty())
    			;
    }
    
    @Test
    public void getAllGrantedPermissionsTest() throws Exception{
    	this.mockMvc.perform(get(mapping))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(jsonPath("$[?(@.permissionName=='Test')]").isNotEmpty())
    			;
    }
    
    @Test
    public void deleteGrantedPermissionTest() throws Exception{
    	GrantedPermissionId granStub = new GrantedPermissionId();
    	granStub.setPermission("Test");
    	granStub.setUnit((short) 1);
    	granStub.setPerson(new Long(1));
    	
    	
    	this.mockMvc.perform(delete(mapping).content(objectMapper.writeValueAsString(granStub)))
    			.andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get(mapping))
    			.andReturn().getResponse().getContentAsString()
    			;
    	GrantedPermission[] perms = objectMapper.readValue(results, GrantedPermission[].class);
    	for(GrantedPermission perm: perms){
    		if(perm.getPerson().getId().equals(new Long(1))){
    			fail("Granted permission found after delete!");
    		}
    	}
    		
    }
}
