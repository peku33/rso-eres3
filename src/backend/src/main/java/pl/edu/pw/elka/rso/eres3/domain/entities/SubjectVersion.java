package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * A version of a subject.
 */
@Entity
public class SubjectVersion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private Character versionCode;

	@NotNull
	@ManyToOne
	private Subject subject;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Character getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(final Character versionCode) {
		this.versionCode = versionCode;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

}
