package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectRealization;

public interface SubjectRealizationRepository extends CrudRepository<SubjectRealization, Integer> {

	Iterable<SubjectRealization> findBySubjectVersionId(Integer subjectVersionId);
}
