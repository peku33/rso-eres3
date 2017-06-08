import {OrganizationalUnit} from './organizational-unit'


export class Person {
  id: number;
  firstName:  string;
  lastName:   string;
  otherNames: string;
  login:      string;
  password:   string;
  pesel:      string;
  unit:       OrganizationalUnit;
}
