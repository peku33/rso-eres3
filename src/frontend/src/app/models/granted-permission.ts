import {Person}       from './person'
import {Permission}   from  './permission'
import {OrganizationalUnit} from './organizationalUnit'

export class GrantedPermission {
  person: Person;
  permission:  Permission;
  organizationalUnit: OrganizationalUnit;
}
