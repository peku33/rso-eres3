package pl.edu.pw.elka.rso.eres3.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Class that represents a user of the system.
 */
@Entity
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(max = 50)
	@Column(length = 50)
	private String firstName;

	@Size(max = 200)
	@Column(length = 200)
	private String otherNames;

	@Size(max = 50)
	@Column(length = 50)
	private String lastName;

	@NotNull
	@Size(min = 4, max = 20)
	@Column(length = 20, unique = true, nullable = false)
	private String login;

	@NotNull
	@Size(min = 60, max = 60)
	@Column(length = 60, nullable = false)
	private String password;

	@Size(min = 11, max = 11)
	@Column(length = 11)
	private String pesel;

	@OneToMany
	private Set<PersonPermissionOnUnit> permissions;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(final String otherNames) {
		this.otherNames = otherNames;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(final String pesel) {
		this.pesel = pesel;
	}

	public Set<PersonPermissionOnUnit> getPermissions() {
		return permissions;
	}

	public void setPermissions(final Set<PersonPermissionOnUnit> permissions) {
		this.permissions = permissions;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}