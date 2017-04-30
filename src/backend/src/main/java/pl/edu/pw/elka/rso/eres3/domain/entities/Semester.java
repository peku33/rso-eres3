package pl.edu.pw.elka.rso.eres3.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A semester on faculty.
 */
@Entity
public class Semester implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Short id;

	@NotNull
	private Short year;

	@Enumerated(EnumType.STRING)
	@NotNull
	private SemesterType type;

	public Semester() {
		//for hibernate
	}

	public Semester(final Short id) {
		setId(id);
	}

	public Short getId() {
		return id;
	}

	public void setId(final Short id) {
		this.id = id;
	}

	public Short getYear() {
		return year;
	}

	public void setYear(final Short year) {
		this.year = year;
	}

	public SemesterType getType() {
		return type;
	}

	public void setType(final SemesterType type) {
		this.type = type;
	}

	public enum SemesterType {
		WINTER, SUMMER
	}
}