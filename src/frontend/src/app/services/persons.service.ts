import {Injectable} from "@angular/core";
import {Person} from "../model/person";
import {BE_URL} from "../settings/backendInfo";
import {Http, Headers} from "@angular/http";

const ADDRESS = BE_URL + "/persons";

@Injectable()
export class PersonsService {
    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {
    }

    public getAllPersons(): Promise<Person[]> {
        return this.http.get(ADDRESS)
            .toPromise()
            .then((response) => {
                return response.json() as Person[];
            });
    }

    public addPerson(person: Person): Promise<Person> {
        return this.http.post(ADDRESS, JSON.stringify(person), {headers: this.headers})
            .toPromise().then(() => person);
    }

    public updatePerson(person: Person): Promise<Person> {
        return null;
    }
}