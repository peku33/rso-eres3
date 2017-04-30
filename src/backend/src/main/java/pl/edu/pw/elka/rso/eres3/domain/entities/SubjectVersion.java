package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

/**
 * A version of a subject.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "id", "version_code" }))
public class SubjectVersion implements Serializable, SimpleIdEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@NotNull
	@Column(name="version_code")
	private Character versionCode;

	@NotNull
	@ManyToOne
	private Subject subject;

	@Override
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
