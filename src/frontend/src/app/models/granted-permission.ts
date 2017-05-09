import {Person}       from './person'
import {Permission}   from  './permission'
import {OrganizationalUnit} from './organizational-unit'

export class GrantedPermission {
  permission:  Permission;
  person: Person;
  organizationalUnit: OrganizationalUnit;
}
