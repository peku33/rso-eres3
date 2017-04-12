package pl.edu.pw.elka.rso.eres3.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

	@ManyToOne
	private Person person;

	@ManyToOne
	private Permission permission;

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
}