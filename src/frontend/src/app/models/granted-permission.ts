import {Person}       from './person'
import {Permission}   from  './permission'
import {OrganizationalUnit} from './organizational-unit'

export class GrantedPermission {
  person: Person;
  permission:  Permission;
  organizationalUnit: OrganizationalUnit;
}
