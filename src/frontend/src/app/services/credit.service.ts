import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Credit } from '../models/credit';
import {BE_URL} from "../settings/backendInfo";


@Injectable()
export class CreditService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private creditsUrl = `${BE_URL}/credits`;  // URL to web api

    constructor(private http: Http) { }

    getAllCredits(): Promise<Credit[]> {
        const url = `${this.creditsUrl}`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json().data as Credit[])
            .catch(this.handleError);
    }

    getTourCredits(id: number): Promise<Credit[]> {
        const url = `${BE_URL}/studenttours/${id}/credits`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json().data as Credit[])
            .catch(this.handleError);
    }

    getSubjectRealizationCredits(id: number): Promise<Credit[]> {
        const url = `${BE_URL}/subjects/${id}/credits`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json().data as Credit[])
            .catch(this.handleError);
    }

    getCredit(id :number): Promise<Credit> {
        const url = `${this.creditsUrl}/${id}`;
        return this.http.get(url)
            .toPromise()
            .then(response => response.json().data as Credit)
            .catch(this.handleError);
    }

    addCredit(credit: Credit): Promise<Credit> {
        return this.http
            .post(this.creditsUrl, credit, {headers: this.headers})
            .toPromise()
            .then(res => res.json().data as Credit)
            .catch(this.handleError);
    }

    updateCredit(credit: Credit): Promise<Credit> {
        const url = `${this.creditsUrl}`;
        return this.http
            .put(url, JSON.stringify(credit), {headers: this.headers})
            .toPromise()
            .then(() => credit)
            .catch(this.handleError);
    }

    deleteCredit(id: number): Promise<void> {
        const url = `${this.creditsUrl}/${id}`;
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
