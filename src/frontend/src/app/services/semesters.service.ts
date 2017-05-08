import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Semester } from '../models/semester';
import {BE_URL} from "../settings/backendInfo";


@Injectable()
export class SemestersService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private semesterUrl = `${BE_URL}/semesters`;  // URL to web api

  constructor(private http: Http) { }

  getSemesters(): Promise<Semester[]> {
    return this.http.get(this.semesterUrl)
               .toPromise()
               .then(response => response.json().data as Semester[])
               .catch(this.handleError);
  }

  getSemester(id: number): Promise<Semester> {
    const url = `${this.semesterUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as Semester)
      .catch(this.handleError);
  }

  // delete(id: number): Promise<void> {
  //   const url = `${this.semesterUrl}/${id}`;
  //   return this.http.delete(url, {headers: this.headers})
  //     .toPromise()
  //     .then(() => null)
  //     .catch(this.handleError);
  // }

  // create(name: string): Promise<Person> {
  //   return this.http
  //     .post(this.personsUrl, JSON.stringify({name: name}), {headers: this.headers})
  //     .toPromise()
  //     .then(res => res.json().data as Person)
  //     .catch(this.handleError);
  // }

  // update(semester: Semester): Promise<Semester> {
  //   const url = `${this.semesterUrl}/${semester.id}`;
  //   return this.http
  //     .put(url, JSON.stringify(semester), {headers: this.headers})
  //     .toPromise()
  //     .then(() => semester)
  //     .catch(this.handleError);
  // }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
