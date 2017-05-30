package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTour;

public interface StudentTourRepository extends CrudRepository<StudentTour, Long>{
	Iterable<StudentTour> findByPersonId(long id);
}
