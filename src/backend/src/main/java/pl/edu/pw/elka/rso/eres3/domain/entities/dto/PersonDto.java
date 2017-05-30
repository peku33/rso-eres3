package pl.edu.pw.elka.rso.eres3.domain.entities.dto;

public class PersonDto {
	private Long id;

	private String firstName;

	private String otherNames;

	private String lastName;

	private String login;

	private String password;

	private String pesel;

	private OrganizationalUnitDto unit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(String otherNames) {
		this.otherNames = otherNames;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public OrganizationalUnitDto getUnit() {
		return unit;
	}

	public void setUnit(OrganizationalUnitDto unit) {
		this.unit = unit;
	}
}
