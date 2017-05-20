package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.Credit;
import pl.edu.pw.elka.rso.eres3.domain.repositories.CreditRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Rest controller for credit.
 *
 * In first version the unchecked validation exceptions thrown by JPA are transmitted to frontend.
 * This may be a subject to change in the future if needed.
 *
 * Created by Jakub Lorenc on 19.05.17.
 */
@RestController
@Transactional
public class CreditController extends AbstractCrudController<Credit, Long> {
    private static final String mapping = "/credits";
    private final CreditRepository creditRepository;

    @Autowired
    public CreditController(CreditRepository repository){
        super(repository, true);
        this.creditRepository = repository;
    }

    @RequestMapping(value = mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'CreditRead')")
    public List<Credit> getAllCredits() {
        return getAll();
    }

    @RequestMapping(value = StudentTourController.mapping + "/{id}" + mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'CreditRead')")
    public List<Credit> getTourCredits(@PathVariable final long id) {
        return creditRepository.getCreditsByTourId(id);
    }

    @RequestMapping(value = SubjectRealizationController.mapping + "/{id}" + mapping, method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'CreditRead')")
    public List<Credit> getSubjectRealizationCredits(@PathVariable final int id) {
        return creditRepository.getCreditsByRealizationId(id);
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(null, 'CreditRead')")
    public ResponseEntity<Credit> getCredit(@PathVariable final long id) {
        return getEntity(id);
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(null, 'CreditCreate')")
    public ResponseEntity<Credit> addCredit(@RequestBody final Credit credit) {
        return addEntity(credit);
    }

    @RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(null, 'CreditUpdate')")
    public ResponseEntity<Credit> updateCredit(@RequestBody final Credit credit) {
        return updateEntity(credit);
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(null, 'CreditDelete')")
    public ResponseEntity<Credit> deleteCredit(@PathVariable final long id) {
        return deleteEntity(id);
    }

    @Override
    protected String getControllerMapping() {
        return mapping;
    }
}
