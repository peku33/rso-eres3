import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Subject } from '../models/subject';
import {BE_URL} from "../settings/backendInfo";


@Injectable()
export class SubjectsService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private subjectsUrl = `${BE_URL}/subjects`;  // URL to web api

  constructor(private http: Http) { }

  getSubjectByUnitId(id: number): Promise<Subject[]> {
    const url = `${BE_URL}/units/${id}/subjects`;
    return this.http.get(url)
               .toPromise()
               .then(response => response.json().data as Subject[])
               .catch(this.handleError);
  }


  getSubject(id: number): Promise<Subject> {
    const url = `${this.subjectsUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as Subject)
      .catch(this.handleError);
  }

  create(subject: Subject): Promise<Subject> {
    return this.http
      .post(this.subjectsUrl, JSON.stringify(subject), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data as Subject)
      .catch(this.handleError);
  }

  update(subject: Subject): Promise<Subject> {
    const url = `${this.subjectsUrl}`;
    return this.http
      .put(url, JSON.stringify(subject), {headers: this.headers})
      .toPromise()
      .then(() => subject)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    const url = `${this.subjectsUrl}/${id}`;
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
