import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { SubjectVersion } from '../models/subject-version';
import {BE_URL}           from "../settings/backendInfo";


@Injectable()
export class SubjectVersionsService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private subjectVersionsUrl = `${BE_URL}/subjects/versions`;  // URL to web api

  constructor(private http: Http) { }

  getBySubjectId(id: number): Promise<SubjectVersion[]> {
    const url = `${BE_URL}/subjects/{id}/versions`;
    return this.http.get(url)
               .toPromise()
               .then(response => response.json().data as SubjectVersion[])
               .catch(this.handleError);
  }


  getSubjectVersion(id: number): Promise<SubjectVersion> {
    const url = `${this.subjectVersionsUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as SubjectVersion)
      .catch(this.handleError);
  }

  create(subjectVersion: SubjectVersion): Promise<SubjectVersion> {
    return this.http
      .post(this.subjectVersionsUrl, JSON.stringify(subjectVersion), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data as SubjectVersion)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    const url = `${this.subjectVersionsUrl}/${id}`;
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
