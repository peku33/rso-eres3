package pl.edu.pw.elka.rso.eres3.dto;

import java.io.Serializable;

/**
 * Created by Jakub Lorenc on 23.04.17.
 */
public class PermissionOnUnitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Short unitId;
    private String permissionName;

    public Short getUnitId() {
        return unitId;
    }

    public void setUnitId(Short unitId) {
        this.unitId = unitId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
