package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

/**
 * Realization of a subject's version on a given semester.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"subject_version_id", "semester_id"}))
public class SubjectRealization implements Serializable, SimpleIdEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@NotNull
	@ManyToOne
	@JoinColumn(name="subject_version_id")
	private SubjectVersion subjectVersion;

	@NotNull
	@ManyToOne
	@JoinColumn(name="semester_id")
	private Semester semester;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public SubjectVersion getSubjectVersion() {
		return subjectVersion;
	}

	public void setSubjectVersion(final SubjectVersion subjectVersion) {
		this.subjectVersion = subjectVersion;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

}
