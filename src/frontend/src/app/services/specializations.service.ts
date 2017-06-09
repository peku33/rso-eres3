import {Injectable}    from '@angular/core';
import {Headers, Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Specialization} from './../models/specialization';
import {BE_URL}           from "../settings/backendInfo";


@Injectable()
export class SpecializationsService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private specializationsUrl = `${BE_URL}/specializations`;  // URL to web api

    constructor(private http: Http) {
    }

    getSpecializationsByUnitId(id: number): Promise<Specialization[]> {
        const url = `${BE_URL}/units/${id}/specializations`;
        return this.http.get(url)
            .toPromise()
            .then((response) => {
                return response.json() as Specialization[];
            });
    }


    getSpecialization(id: number): Promise<Specialization> {
        const url = `${this.specializationsUrl}/${id}`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json() as Specialization)
            .catch(this.handleError);
    }

    createSpecialization(specialization: Specialization): Promise<Specialization> {
        return this.http
            .post(this.specializationsUrl, JSON.stringify(specialization), {headers: this.headers})
            .toPromise()
            .then(res => res.json().data as Specialization)
            .catch(this.handleError);
    }

    updateSpecialization(specialization: Specialization): Promise<Specialization> {
        return this.http
            .put(this.specializationsUrl, JSON.stringify(specialization), {headers: this.headers})
            .toPromise()
            .then(() => specialization)
            .catch(this.handleError);
    }

    deleteSpecialization(id: number): Promise<void> {
        const url = `${this.specializationsUrl}/${id}`;
        return this.http.delete(url, {headers: this.headers})
            .toPromise()
            .then(() => null)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
