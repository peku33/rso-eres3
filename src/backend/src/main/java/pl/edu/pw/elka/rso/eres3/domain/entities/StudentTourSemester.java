package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

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
 * A single semester of student's tour;
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"student_tour_id", "semester_id"}))
public class StudentTourSemester implements Serializable, SimpleIdEntity<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private Byte nominalSemesterNo;

	@NotNull
	@ManyToOne
	@JoinColumn(name="student_tour_id")
	private StudentTour tour;

	@NotNull
	@ManyToOne
	@JoinColumn(name="semester_id")
	private Semester semester;

	@NotNull
	@ManyToOne
	private Specialization specialization;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Byte getNominalSemesterNo() {
		return nominalSemesterNo;
	}

	public void setNominalSemesterNo(final Byte nominalSemesterNo) {
		this.nominalSemesterNo = nominalSemesterNo;
	}

	public StudentTour getTour() {
		return tour;
	}

	public void setTour(final StudentTour tour) {
		this.tour = tour;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(final Semester semester) {
		this.semester = semester;
	}

	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(final Specialization specialization) {
		this.specialization = specialization;
	}
}
