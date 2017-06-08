package pl.edu.pw.elka.rso.eres3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.rso.eres3.controllers.abstractions.AbstractCrudController;
import pl.edu.pw.elka.rso.eres3.domain.entities.OrganizationalUnit;
import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;
import pl.edu.pw.elka.rso.eres3.domain.repositories.OrganizationalUnitRepository;
import pl.edu.pw.elka.rso.eres3.domain.repositories.SpecializationRepository;
import pl.edu.pw.elka.rso.eres3.dtos.SpecializationDto;

import javax.transaction.Transactional;

/**
 * Rest controller for specializations.
 */
@RestController
@Transactional
public class SpecializationController extends AbstractCrudController<Specialization, Integer> {
    private static final String mapping = "/specializations";
    private final SpecializationRepository specializationRepo;
    private final OrganizationalUnitRepository unitRepo;

    @Autowired
    public SpecializationController(final SpecializationRepository specializationRepo,
                                    final OrganizationalUnitRepository unitRepo) {
        super(specializationRepo, true);
        this.specializationRepo = specializationRepo;
        this.unitRepo = unitRepo;
    }

	@RequestMapping(value = "units/{id}" + mapping, method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'OrganizationalUnit', 'SpecializationRead')")
	public Iterable<Specialization> getAllSpecializationsOnUnit(@PathVariable final short id){
		return specializationRepo.findByUnitId(id);
	}

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'Specialization', 'SpecializationRead')")
    public SpecializationDto getSpecialization(@PathVariable final int id) {
        return new SpecializationDto(specializationRepo.findById(id));
    }

    @RequestMapping(value = mapping, method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#specialization, 'SpecializationCreate')")
    public SpecializationDto addSpecialization(@RequestBody final SpecializationDto dto) {
        Specialization specialization = createSpecializationFromDto(dto);
        specializationRepo.save(specialization);
        return new SpecializationDto(specialization);
    }

    @RequestMapping(value = mapping, method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#specialization, 'SpecializationUpdate')")
    public SpecializationDto updateSpecialization(@RequestBody final SpecializationDto dto) {
        Specialization specialization = specializationRepo.findById(dto.id);
        specialization.updateFromDto(dto);
        specializationRepo.save(specialization);
        return new SpecializationDto(specialization);
    }

    @RequestMapping(value = mapping + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'Specialization', 'SpecializationDelete')")
    public void deleteSpecialization(@PathVariable final int id) {
        specializationRepo.removeById(id);
    }

    @Override
    protected String getControllerMapping() {
        return mapping;
    }

    private Specialization createSpecializationFromDto(SpecializationDto dto) {
        Specialization parent = null;
        OrganizationalUnit unit = null;
        if (dto.superSpecializationId != null)
            parent = specializationRepo.findById(dto.superSpecializationId);
        if (dto.unitId != null)
            unit = unitRepo.findOne(dto.unitId);
        return new Specialization(dto.fullName, dto.shortName, dto.type, parent, unit);
    }
}