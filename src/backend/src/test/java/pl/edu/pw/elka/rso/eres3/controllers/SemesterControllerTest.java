package pl.edu.pw.elka.rso.eres3.controllers;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.elka.rso.eres3.domain.entities.Semester;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SemesterRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Semester rest controller tests.
 */
public class SemesterControllerTest {
	private static final ResponseEntity notFound = ResponseEntity.notFound().build();
	private SemesterRepository mockSemesterRepository;
	private SemesterController semesterController;

	@Before
	public void setUp() {
		mockSemesterRepository = mock(SemesterRepository.class);
		semesterController = new SemesterController(mockSemesterRepository);
	}

	@Test
	public void testGetAllSemesters() throws Exception {
		final short unitId = (short)1;
		final ImmutableSet<Semester> expectedSemesters = ImmutableSet.of(
				new Semester((short)1),
				new Semester((short)2));
		when(mockSemesterRepository.findAll()).thenReturn(expectedSemesters);
		final List<Semester> allSemesters = semesterController.getAllSemesters();
		assertThat(allSemesters).containsOnlyElementsOf(expectedSemesters);
	}

	@Test
	public void testGetSemester() throws Exception {
		final Semester semester = new Semester((short)1);
		when(mockSemesterRepository.findOne((short)1)).thenReturn(semester);
		when(mockSemesterRepository.findOne((short)2)).thenReturn(null);
		assertThat(semesterController.getSemester((short)1).getBody()).isEqualTo(semester);
		assertThat(semesterController.getSemester((short)2)).isEqualTo(notFound);
	}
}