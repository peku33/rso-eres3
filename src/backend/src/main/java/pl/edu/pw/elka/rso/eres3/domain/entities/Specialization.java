package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Specialization on a faculty.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"unit_id", "short_name"}))
public class Specialization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@Size(min = 1, max = 200)
	@Column(length = 200, nullable = false)
	private String fullName;

	@NotNull
	@Size(min = 1, max = 20)
	@Column(length = 20, nullable = false, name="short_name")
	private String shortName;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SpecializationType type;

	@NotNull
	@ManyToOne
	@JoinColumn(name="unit_id")
	private OrganizationalUnit unit;

	@ManyToOne
	private Specialization superSpecialization;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public SpecializationType getType() {
		return type;
	}

	public void setType(final SpecializationType type) {
		this.type = type;
	}

	public OrganizationalUnit getUnit() {
		return unit;
	}

	public void setUnit(final OrganizationalUnit unit) {
		this.unit = unit;
	}

	public Specialization getSuperSpecialization() {
		return superSpecialization;
	}

	public void setSuperSpecialization(final Specialization superSpecialization) {
		this.superSpecialization = superSpecialization;
	}

	public enum SpecializationType {
		FACULTY, MAJOR, SPECIALITY, PATH
	}
}
