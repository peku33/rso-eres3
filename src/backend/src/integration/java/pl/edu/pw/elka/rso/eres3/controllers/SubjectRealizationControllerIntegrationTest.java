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
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectRealization;
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectVersion;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.entities.Semester;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubjectRealizationControllerIntegrationTest {
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
    
    private static final String mapping = "/subjects/versions/realizations";
    
    private static Subject subjectStub = new Subject();
    private static SubjectVersion versionStub = new SubjectVersion();
    private static OrganizationalUnit unitStub = new OrganizationalUnit(); 
    private static Semester semesterStub = new Semester();

    @Before
    public void setup() throws SQLException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        String sql = "insert into organizational_unit(id, full_name, short_name) values(1, \"test\", \"test\")";;
        stmt.executeUpdate(sql);
        sql = "insert into subject(id, didactical_units, ects, full_name, short_name, type, unit_id) values"
        		+ "(1, 3, 4, \"Test\", \"Test\", \"EXAM\", 1);";
        stmt.executeUpdate(sql);
        sql = "insert into subject_version(id, version_code, subject_id) values(1, '1', 1), (2, '2', 1), (3, '3', 1);";
        stmt.executeUpdate(sql);
        sql = "insert into semester(id, type, year) values(1, \"WINTER\", 1918);";
        stmt.executeUpdate(sql);
        sql = "insert into subject_realization(id, semester_id, subject_version_id) values(1, 1, 1), (2, 1, 2);";
        stmt.executeUpdate(sql);
        conn.close();
        
        unitStub.setFullName("test");
    	unitStub.setShortName("test");
    	unitStub.setId((short) 1);
        
        subjectStub.setFullName("Test");
    	subjectStub.setShortName("Test");
    	subjectStub.setEcts((byte)4);
    	subjectStub.setDidacticalUnits((byte) 3);
    	subjectStub.setType(Subject.SubjectType.EXAM);
    	subjectStub.setUnit(unitStub);
    	subjectStub.setId(1);
    	
    	versionStub.setSubject(subjectStub);
    	versionStub.setVersionCode('3');
    	versionStub.setId(3);
    	
    	semesterStub.setType(Semester.SemesterType.WINTER);
    	semesterStub.setYear((short) 1918);
    	semesterStub.setId((short) 1);
        
    }
    
   @After
    public void clean() throws SQLException{
    	Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement stmt = conn.createStatement();
        
        String sql = "delete from subject_realization where semester_id=1;"; 
        stmt.executeUpdate(sql);		
        sql = "delete from subject_version where id<4;"; 
        stmt.executeUpdate(sql);
        sql = "delete from subject where id=1;";
        stmt.executeUpdate(sql);
        sql = "delete from organizational_unit where id=1;";
        stmt.executeUpdate(sql);
        sql = "delete from semester where id=1;"; 
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    @Test
    public void addSubjectRealizationTest() throws Exception{
    	SubjectRealization realizationStub = new SubjectRealization();
    	realizationStub.setSemester(semesterStub);
    	realizationStub.setSubjectVersion(versionStub);
    	
    	
    	this.mockMvc.perform(post(mapping).contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(realizationStub))
    			)
    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void getAllSubjectRealizationsTest() throws Exception{
    	String results = this.mockMvc.perform(get("/subjects/versions/1/realizations"))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andReturn().getResponse().getContentAsString()
    			;
    	SubjectRealization[] realizations = objectMapper.readValue(results, SubjectRealization[].class);
    	long id = 0;
    	for(SubjectRealization realization: realizations){
    		if(!realization.getSemester().getId().equals(1)){
    			id = realization.getId();
    			break;
    		}
    	}
    	if(id==0){
    		fail("Unit not found!");
    	}
    }
    
    @Test
    public void getSubjectRealizationTest() throws Exception{
    	this.mockMvc.perform(get(mapping + "/2"))
    			.andExpect(status().is2xxSuccessful())
    			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
    
    @Test
    public void deleteSubjectRealization() throws Exception{
    	this.mockMvc.perform(delete(mapping+"/1")).andExpect(status().isNoContent());
    	
    	String results = this.mockMvc.perform(get("/subjects/versions/1/realizations"))
    			.andReturn().getResponse().getContentAsString();
    	
    	SubjectRealization[] realizations = objectMapper.readValue(results, SubjectRealization[].class);
    	long id = 0;
    	for(SubjectRealization realization: realizations){
    		if(realization.getId().equals(1)){
    			fail("Realization found after delete!");
    		}
    	}
    		
    }
}
