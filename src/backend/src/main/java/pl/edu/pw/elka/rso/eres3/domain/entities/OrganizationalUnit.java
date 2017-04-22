package pl.edu.pw.elka.rso.eres3.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An organizational unit, which is a root institution to which permissions can
 * be assigned for people. In this system it simply identifies a faculty.
 */
@Entity
public class OrganizationalUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Short id;

	@NotNull
	@Size(min = 1, max = 200)
	@Column(length = 200, nullable = false)
	private String fullName;

	@NotNull
	@Size(min = 1, max = 20)
	@Column(length = 20, nullable = false)
	private String shortName;

	public Short getId() {
		return id;
	}

	public void setId(final Short id) {
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
}