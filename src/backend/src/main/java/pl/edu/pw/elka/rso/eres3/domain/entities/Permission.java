package pl.edu.pw.elka.rso.eres3.domain.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

/**
 * Class that represents a permission in a system. Permission is simply a name
 * by which it is identified.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Permission implements Serializable, SimpleIdEntity<String> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Id
	@Size(min = 1, max = 50)
	@Column(length = 50, name="name")
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
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Permission that = (Permission) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description);
	}
}