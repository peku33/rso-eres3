package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTourSemester;

public interface StudentTourSemesterRepository extends CrudRepository<StudentTourSemester, Long> {
	Iterable<StudentTourSemester> findByTourId(final long id);
}
