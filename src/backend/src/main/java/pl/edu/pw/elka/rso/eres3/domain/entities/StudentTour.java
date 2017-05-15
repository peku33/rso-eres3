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
import javax.validation.constraints.Size;

import pl.edu.pw.elka.rso.eres3.domain.entities.abstractions.SimpleIdEntity;

/**
 * A single tour of studies for a student.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"album_no"}))
public class StudentTour implements Serializable, SimpleIdEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(max = 20)
	@Column(length = 20, name="album_no")
	private String albumNo;

	@NotNull
	@ManyToOne
	private Person person;

	@NotNull
	@ManyToOne
	private OrganizationalUnit unit;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getAlbumNo() {
		return albumNo;
	}

	public void setAlbumNo(final String albumNo) {
		this.albumNo = albumNo;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

	public OrganizationalUnit getUnit() {
		return unit;
	}

	public void setUnit(final OrganizationalUnit organizationalUnit) {
		this.unit = organizationalUnit;
	}
}