import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { StudentTourSemester } from '../models/studentTour-semester';
import {BE_URL} from "../settings/backendInfo";


@Injectable()
export class StudentTourSemesterService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private studentToursSemesterUrl = `${BE_URL}/studenttoursemesters`;  // URL to web api

    constructor(private http: Http) { }

    getAllStudentToursSemestersByTourId(id: number): Promise<StudentTourSemester[]> {
        const url = `${BE_URL}/studenttours/${id}/studenttoursemesters`;
        return this.http.get(url)
            .toPromise()
            .then((response) => {
                return response.json() as StudentTourSemester[];
            });
    }

    getStudentTourSemester(id: number): Promise<StudentTourSemester> {
        const url = `${this.studentToursSemesterUrl}/${id}`;
        return this.http.get(url)
            .toPromise()
            .then((response) => {
                return response.json() as StudentTourSemester;
            });
    }

    addStudentTourSemester(studentTourSemester: StudentTourSemester): Promise<StudentTourSemester> {
        return this.http
            .post(this.studentToursSemesterUrl, studentTourSemester, {headers: this.headers})
            .toPromise()
            .then(res => res.json().data as StudentTourSemester)
            .catch(this.handleError);
    }

    updateStudentTourSemester(studentTourSemester: StudentTourSemester): Promise<StudentTourSemester> {
        const url = `${this.studentToursSemesterUrl}`;
        return this.http
            .put(url, studentTourSemester, {headers: this.headers})
            .toPromise()
            .then(() => studentTourSemester)
            .catch(this.handleError);
    }

    deleteStudentTourSemester(id: number): Promise<void> {
        const url = `${this.studentToursSemesterUrl}/${id}`;
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
