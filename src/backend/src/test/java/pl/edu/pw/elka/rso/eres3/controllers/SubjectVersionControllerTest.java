package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectVersion;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectVersionRepository;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubjectVersionControllerTest {

	private SubjectVersionRepository mockVersionRepo;
	private SubjectVersionController controller;

	@Before
	public void setUp() throws Exception {
		mockVersionRepo = mock(SubjectVersionRepository.class);
		controller = new SubjectVersionController(mockVersionRepo);
	}

	@Test
	public void testAddSubjectVersion_noVersionExists_versionCodeIsA() throws Exception {
		//given
		final Integer subjectId = 1;
		final SubjectVersion versionToAdd = mockVersion(null, subjectId);
		when(mockVersionRepo.findBySubjectId(subjectId)).thenReturn(Collections.emptyList());
		when(mockVersionRepo.save(versionToAdd)).thenReturn(versionToAdd);
		//when
		final ResponseEntity<SubjectVersion> response = controller.addSubjectVersion(versionToAdd);
		//then
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getVersionCode()).isEqualTo('A');
	}

	@Test
	public void testAddSubjectVersion_versionsExist_versionCodeIsIncremented() throws Exception {
		//given
		final Integer subjectId = 1;
		final SubjectVersion versionToAdd = mockVersion(null, subjectId);
		when(mockVersionRepo.findBySubjectId(subjectId)).thenReturn(Lists.newArrayList(
				mockVersion('A', subjectId), mockVersion('C', subjectId)));
		when(mockVersionRepo.save(versionToAdd)).thenReturn(versionToAdd);
		//when
		final ResponseEntity<SubjectVersion> response = controller.addSubjectVersion(versionToAdd);
		//then
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getVersionCode()).isEqualTo('D');
	}

	private SubjectVersion mockVersion(final Character code, final int subjectId) {
		final SubjectVersion version = new SubjectVersion();
		version.setVersionCode(code);
		version.setSubject(new Subject(subjectId));
		return version;
	}

}
