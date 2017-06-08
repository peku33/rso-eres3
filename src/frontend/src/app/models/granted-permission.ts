import {Person}       from './person'
import {Permission}   from  './permission'
import {OrganizationalUnit} from './organizational-unit'

export class GrantedPermission {
  permission:  Permission;
  person: Person;
  unit: OrganizationalUnit;
  constructor(permission: Permission, person: Person, organizationalUnit: OrganizationalUnit){
    this.permission = permission;
    this.person = person;
    this.unit = organizationalUnit;
  }
}
