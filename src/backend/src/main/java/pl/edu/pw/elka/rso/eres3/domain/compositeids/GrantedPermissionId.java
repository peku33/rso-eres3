package pl.edu.pw.elka.rso.eres3.domain.compositeids;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
public class PersonPermissionOnUnitId implements Serializable {
    private static final long serialVersionUID = 1L;

    private long person;

    private String permission;

    private short unit;

    public long getPerson() {
        return person;
    }

    public void setPerson(long person) {
        this.person = person;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public short getUnit() {
        return unit;
    }

    public void setUnit(short unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonPermissionOnUnitId that = (PersonPermissionOnUnitId) o;
        return person == that.person &&
                unit == that.unit &&
                Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, permission, unit);
    }
}
