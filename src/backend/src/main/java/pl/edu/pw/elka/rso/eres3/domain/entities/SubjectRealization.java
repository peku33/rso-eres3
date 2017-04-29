package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Realization of a subject's version on a given semester.
 */
@Entity
public class SubjectRealization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@ManyToOne
	private SubjectVersion version;

	@NotNull
	@ManyToOne
	private Semester semester;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public SubjectVersion getVersion() {
		return version;
	}

	public void setVersion(final SubjectVersion version) {
		this.version = version;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

}
