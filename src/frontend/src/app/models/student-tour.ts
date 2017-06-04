import {OrganizationalUnit} from './organizational-unit'
import {Person} from "./person";


export class StudentTour {
    id: number;
    albumNo:  string;
    person: Person;
    organizationalUnit: OrganizationalUnit;
}
