import {Injectable} from "@angular/core";
import {BE_URL} from "../settings/backendInfo";
import {Http} from "@angular/http";
import {Unit} from "../model/unit";
@Injectable()
export class UnitsService {
    constructor(private http: Http) {
    }

    public getAllUnits(): Promise<Unit[]> {
        return this.http.get(BE_URL + "/units")
            .toPromise()
            .then((response) => {
                return response.json() as Unit[];
            });
    }
}