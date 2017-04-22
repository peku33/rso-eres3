package pl.edu.pw.elka.rso.eres3.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * A class representing a permission on a unit assigned to a person.
 *
 * Such an assignment basically means that given person has a
 * permission to perform some action on objects that are in the
 * organizational tree of given organizational unit.
 */
@Entity
public class PersonPermissionOnUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private Person person;

	@Id

	@ManyToOne
	private Permission permission;

	@Id
	@ManyToOne
	private OrganizationalUnit unit;

	public Person getPerson() {
		return person;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(final Permission permission) {
		this.permission = permission;
	}

	public OrganizationalUnit getUnit() {
		return unit;
	}

	public void setUnit(final OrganizationalUnit unit) {
		this.unit = unit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersonPermissionOnUnit that = (PersonPermissionOnUnit) o;
		return Objects.equals(person, that.person) &&
				Objects.equals(permission, that.permission) &&
				Objects.equals(unit, that.unit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(person, permission, unit);
	}
}