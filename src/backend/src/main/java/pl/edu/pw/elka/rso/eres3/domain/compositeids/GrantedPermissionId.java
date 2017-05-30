package pl.edu.pw.elka.rso.eres3.domain.compositeids;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Jakub Lorenc on 30.04.17.
 */
public class GrantedPermissionId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long person;

    private String permission;

    private Short unit;

    public GrantedPermissionId(){
        //for hibernate
    }

    public GrantedPermissionId(final Long personId, final String permissionName, final Short unitId){
        this.person = personId;
        this.permission = permissionName;
        this.unit = unitId;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Short getUnit() {
        return unit;
    }

    public void setUnit(Short unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrantedPermissionId that = (GrantedPermissionId) o;
        return person.equals(that.person) &&
                unit.equals(that.unit) &&
                Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, permission, unit);
    }
}
