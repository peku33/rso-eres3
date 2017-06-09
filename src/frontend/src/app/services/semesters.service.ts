import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Semester } from '../models/semester';
import {BE_URL}     from "../settings/backendInfo";


@Injectable()
export class SemestersService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private semesterUrl = `${BE_URL}/semesters`;  // URL to web api

  constructor(private http: Http) { }

  getSemesters(): Promise<Semester[]> {
    return this.http.get(this.semesterUrl)
        .toPromise()
        .then((response) => {
          return response.json() as Semester[];
        });
  }

  getSemester(id: number): Promise<Semester> {
    const url = `${this.semesterUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as Semester)
      .catch(this.handleError);
  }
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
