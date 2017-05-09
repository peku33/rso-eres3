import {OrganizationalUnit} from './organizational-unit'


export class Specialization {
  id: number;
  fullName:  string;
  shortName: string;
  specializationType:   string;
  super_specialization_id: number; //?? było w bazie danych
  organizationalUnit: OrganizationalUnit;
}
