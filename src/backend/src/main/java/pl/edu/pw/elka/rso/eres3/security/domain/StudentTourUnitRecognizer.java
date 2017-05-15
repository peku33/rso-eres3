package pl.edu.pw.elka.rso.eres3.security.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import pl.edu.pw.elka.rso.eres3.domain.entities.StudentTour;
import pl.edu.pw.elka.rso.eres3.domain.repositories.StudentTourRepository;

/**
 * Unit recognizer for student's tours.
 */
@Component
public class StudentTourUnitRecognizer extends EntityUnitRecognizer {

	private final StudentTourRepository repo;

	public StudentTourUnitRecognizer(final StudentTourRepository repo) {
		this.repo = repo;

	}

	@Override
	public Class<?> getRecognizableClass() {
		return StudentTour.class;
	}

	@Override
	public Short getUnitIdByEntity(final Object entity) {
		final StudentTour tour = (StudentTour) entity;
		return tour.getUnit().getId();
	}

	@Override
	public Short getUnitIdByEntityId(final Serializable entityId) {
		final StudentTour tour = repo.findOne((Long) entityId);
		return getUnitIdByEntity(tour);
	}

}
