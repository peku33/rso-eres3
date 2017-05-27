package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;

import java.util.List;

public interface SpecializationRepository extends CrudRepository<Specialization, Integer> {
    List<Specialization> findByUnitId(Short unitId);

    Specialization findById(Integer id);

    void removeById(Integer id);
}
