import {Injectable} from "@angular/core";
import {BE_URL} from "../settings/backendInfo";
import {Http, Headers} from "@angular/http";
import {Unit} from "../model/unit";

const ADDRESS: string = BE_URL + "/units";
@Injectable()
export class UnitsService {
    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {
    }

    public getAllUnits(): Promise<Unit[]> {
        return this.http.get(ADDRESS)
            .toPromise()
            .then((response) => {
                return response.json() as Unit[];
            });
    }

    public getUnit(id: number): Promise<Unit> {
        return this.http.get(ADDRESS + "/" + id)
            .toPromise()
            .then((response) => {
                return response.json() as Unit;
            });
    }

    public addUnit(unit: Unit): Promise<Unit> {
        return this.http.post(ADDRESS, JSON.stringify(unit), {headers: this.headers})
            .toPromise().then(() => unit);
    }

    public editUnit(unit: Unit): Promise<Unit> {
        return this.http.put(ADDRESS, JSON.stringify(unit), {headers: this.headers})
            .toPromise().then(() => unit);
    }

    public deleteUnit(id: number): Promise<Unit> {
        return this.http.delete(ADDRESS + "/" + id)
            .toPromise()
            .then((response) => {
                return response.json() as Unit;
            });
    }
}