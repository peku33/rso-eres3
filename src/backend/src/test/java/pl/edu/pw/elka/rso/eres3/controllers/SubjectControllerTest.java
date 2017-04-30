package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SubjectRepository;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Subject rest controller tests.
 */
public class SubjectControllerTest {
	private static final ResponseEntity badRequest = ResponseEntity.badRequest().build();
	private static final ResponseEntity notFound = ResponseEntity.notFound().build();
	private SubjectRepository mockSubjectRepository;
	private SubjectController subjectController;

	@Before
	public void setUp() {
		mockSubjectRepository = mock(SubjectRepository.class);
		subjectController = new SubjectController(mockSubjectRepository);
	}

	@Test
	public void testGetAllSubjectsOnUnit() throws Exception {
		final short unitId = (short)1;
		final ImmutableSet<Subject> expectedSubjects = ImmutableSet.of(
				new Subject(1),
				new Subject(2));
		when(mockSubjectRepository.findByUnitId(unitId)).thenReturn(expectedSubjects);
		final List<Subject> subjects = subjectController.getAllSubjectsOnUnit(unitId);
		assertThat(subjects).containsOnlyElementsOf(expectedSubjects);
	}

	@Test
	public void testGetSubject() throws Exception {
		final Subject subject = new Subject(1);
		when(mockSubjectRepository.findOne(1)).thenReturn(subject);
		when(mockSubjectRepository.findOne(2)).thenReturn(null);
		assertThat(subjectController.getSubject(1).getBody()).isEqualTo(subject);
		assertThat(subjectController.getSubject(2)).isEqualTo(notFound);
	}

	@Test
	public void testAddSubject_cannotAddWithId() throws Exception {
		final Subject subjectWithId = new Subject(1);
		assertThat(subjectController.addSubject(subjectWithId)).isEqualTo(badRequest);
	}

	@Test
	public void testAddSubject_canAddWithoutId() throws Exception {
		final Subject subjectWithId = new Subject(1);
		final Subject newSubject = new Subject();
		when(mockSubjectRepository.save(newSubject)).thenReturn(subjectWithId);
		final ResponseEntity<Subject> okRequest = subjectController.addSubject(newSubject);
		assertThat(okRequest.getBody().getId()).isEqualTo(1);
		assertThat(okRequest.getHeaders().getLocation()).isEqualTo(URI.create("/subjects/1"));
	}

	@Test
	public void testUpdateSubject_cannotUpdateWithoutId() throws Exception {
		final Subject subject = new Subject();
		assertThat(subjectController.updateSubject(subject)).isEqualTo(badRequest);
	}

	@Test
	public void testUpdateSubject_cannotUpdateIfDoesntExist() throws Exception {
		final Subject subject = new Subject(1);
		assertThat(subjectController.updateSubject(subject)).isEqualTo(notFound);
	}

	@Test
	public void testUpdateSubject_canUpdateIfExists() throws Exception {
		final Subject subject = new Subject(1);
		final Subject subjectAfterUpdate = new Subject(1);
		when(mockSubjectRepository.exists(1)).thenReturn(true);
		when(mockSubjectRepository.save(subject)).thenReturn(subjectAfterUpdate);
		final ResponseEntity<Subject> goodRequest = subjectController.updateSubject(subject);
		assertThat(goodRequest.getBody()).isEqualTo(subjectAfterUpdate);
		assertThat(goodRequest.getStatusCode().is2xxSuccessful()).isTrue();
	}

	@Test
	public void testDeleteSubject_cannotDeleteWhenNotFound() throws Exception {
		assertThat(subjectController.deleteSubject(1)).isEqualTo(notFound);
	}

	@Test
	public void testDeleteSubject_canDeleteIfFound() throws Exception {
		when(mockSubjectRepository.exists(1)).thenReturn(true);
		final ResponseEntity<Subject> goodRequest = subjectController.deleteSubject(1);
		assertThat(goodRequest.getStatusCode().is2xxSuccessful()).isTrue();
	}
}