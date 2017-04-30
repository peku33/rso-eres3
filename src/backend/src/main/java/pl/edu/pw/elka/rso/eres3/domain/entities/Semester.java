package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

/**
 * A semester on faculty.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"year", "type"}))
public class Semester implements Serializable, SimpleIdEntity<Short> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Short id;

	@NotNull
	@Column(name="year")
	private Short year;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name="type")
	private SemesterType type;

	public Semester() {
		//for hibernate
	}

	public Semester(final Short id) {
		setId(id);
	}

	@Override
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