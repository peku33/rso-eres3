package pl.edu.pw.elka.rso.eres3.domain.entities;

import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class that represents a permission in a system. Permission is simply a name
 * by which it is identified.
 */
@Entity
public class Permission implements Serializable, SimpleIdEntity<String> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Id
	@Size(min = 1, max = 50)
	@Column(length = 50)
	private String name;

	@Size(max = 200)
	@Column(length = 200)
	private String description;

	@Override
	public String getId(){
		return name;
	}

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Permission that = (Permission) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description);
	}
}