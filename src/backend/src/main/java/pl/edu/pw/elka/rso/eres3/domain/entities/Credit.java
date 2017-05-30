package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

/**
 * A passed subject realization by student on a specified tour.
 */
@Entity
public class Credit implements Serializable, SimpleIdEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	private StudentTour tour;

	@NotNull
	@ManyToOne
	private SubjectRealization realization;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public StudentTour getTour() {
		return tour;
	}

	public void setTour(final StudentTour tour) {
		this.tour = tour;
	}

	public SubjectRealization getRealization() {
		return realization;
	}

	public void setRealization(final SubjectRealization realization) {
		this.realization = realization;
	}
}