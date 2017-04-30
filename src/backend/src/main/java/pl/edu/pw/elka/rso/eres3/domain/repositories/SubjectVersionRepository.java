package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.edu.pw.elka.rso.eres3.domain.entities.SubjectVersion;

public interface SubjectVersionRepository extends CrudRepository<SubjectVersion, Integer> {
	Iterable<SubjectVersion> findBySubjectId(Integer subjectId);
}
