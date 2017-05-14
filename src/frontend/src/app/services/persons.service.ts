import {Injectable} from "@angular/core";
import {Person} from "../model/person";
import {BE_URL} from "../settings/backendInfo";
import {Http} from "@angular/http";
@Injectable()
export class PersonsService {
    constructor(private http: Http) {
    }

    public getAllPersons(): Promise<Person[]> {
        return this.http.get(BE_URL + "/persons")
            .toPromise()
            .then((response) => {
                return response.json() as Person[];
            });
    }
}