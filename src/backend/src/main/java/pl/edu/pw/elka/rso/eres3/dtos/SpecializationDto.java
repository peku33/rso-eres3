package pl.edu.pw.elka.rso.eres3.dtos;

import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization;
import pl.edu.pw.elka.rso.eres3.domain.entities.Specialization.SpecializationType;

public class SpecializationDto {
    public SpecializationDto() {
    }

    public SpecializationDto(Specialization specialization) {
        this.id = specialization.getId();
        this.fullName = specialization.getFullName();
        this.shortName = specialization.getShortName();
        this.type = specialization.getType();
        this.superSpecializationId = specialization.getSuperSpecialization() == null ? null : specialization.getSuperSpecialization().getId();
        this.unitId = specialization.getUnit().getId();
    }

    public Integer id;
    public String fullName;
    public String shortName;
    public SpecializationType type;
    public Short unitId;
    public Integer superSpecializationId;
}
