package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.entities.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
    Iterable<Subject> findByUnitId(Short unitId);
}