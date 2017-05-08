package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;

public interface SpecializationRepository extends CrudRepository<Specialization, Integer>{
	Iterable<Specialization> findByUnitId(Short unitId);
}
