package pl.edu.pw.elka.rso.eres3.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class that represents a permission in a system. Permission is simply a name
 * by which it is identified.
 */
@Entity
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Id
	@Size(min = 1, max = 50)
	@Column(length = 50)
	private String name;

	@Size(max = 200)
	@Column(length = 200)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}