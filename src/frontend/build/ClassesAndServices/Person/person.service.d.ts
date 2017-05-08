import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Person } from './person';
export declare class PersonService {
    private http;
    private headers;
    private personsUrl;
    constructor(http: Http);
    getPersons(): Promise<Person[]>;
    getPerson(id: number): Promise<Person>;
    delete(id: number): Promise<void>;
    update(person: Person): Promise<Person>;
    private handleError(error);
}
