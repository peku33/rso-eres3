import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { StudentTour } from '../models/student-tour';
import {BE_URL} from "../settings/backendInfo";


@Injectable()
export class StudentTourService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private studentToursUrl = `${BE_URL}/studenttours`;  // URL to web api

    constructor(private http: Http) { }

    getAllStudentToursByPersonId(id: number): Promise<StudentTour[]> {
        const url = `${BE_URL}/persons/${id}/studenttours`;
        console.log('URL: ' + url);
        return this.http.get(url)
            .toPromise()
            .then((response) => {
                return response.json() as StudentTour[];
            });
    }

    getStudentTour(id: number): Promise<StudentTour> {
        const url = `${this.studentToursUrl}/${id}`;
        console.log('URL: ' + url);
        return this.http.get(url)
            .toPromise()
            .then((response) => {
                return response.json() as StudentTour;
            });
    }

    addStudentTour(studentTour: StudentTour): Promise<StudentTour> {
        return this.http
            .post(this.studentToursUrl, studentTour, {headers: this.headers})
            .toPromise()
            .then(res => res.json().data as StudentTour)
            .catch(this.handleError);
    }

    updateStudentTour(studentTour: StudentTour): Promise<StudentTour> {
        const url = `${this.studentToursUrl}`;
        return this.http
            .put(url, studentTour, {headers: this.headers})
            .toPromise()
            .then(() => studentTour)
            .catch(this.handleError);
    }

    deleteStudentTour(id: number): Promise<void> {
        const url = `${this.studentToursUrl}/${id}`;
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
