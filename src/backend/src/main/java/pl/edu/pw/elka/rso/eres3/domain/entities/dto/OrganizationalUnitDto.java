package pl.edu.pw.elka.rso.eres3.domain.entities.dto;

public class OrganizationalUnitDto {
	private Short id;

	private String fullName;

	private String shortName;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
