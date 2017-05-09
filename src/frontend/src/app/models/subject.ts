import {OrganizationalUnit} from './organizational-unit'


export class Subject {
  id: number;
  fullName:  string;
  shortName: string;
  didacticalUnits:   number;
  ects:      number;
  type:      string;
  organizationalUnit: OrganizationalUnit;
}
