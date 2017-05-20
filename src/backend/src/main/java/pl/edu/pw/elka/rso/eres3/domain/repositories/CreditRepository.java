package pl.edu.pw.elka.rso.eres3.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.elka.rso.eres3.domain.entities.Credit;

import java.util.List;

public interface CreditRepository extends CrudRepository<Credit, Long> {
    List<Credit> getCreditsByTourId(Long tourId);
    List<Credit> getCreditsByRealizationId(Integer realizationId);
}
