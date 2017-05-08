import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { OrganizationalUnit } from './../models/organizational-unit';
import {BE_URL} from "../settings/backendInfo";


@Injectable()
export class OrganizationalUnitsService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private unitsUrl = `${BE_URL}/units`;  // URL to web api

  constructor(private http: Http) { }

  getUnits(): Promise<OrganizationalUnit[]> {
    return this.http.get(this.unitsUrl)
               .toPromise()
               .then(response => response.json().data as OrganizationalUnit[])
               .catch(this.handleError);
  }


  getUnit(id: number): Promise<OrganizationalUnit> {
    const url = `${this.unitsUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as OrganizationalUnit)
      .catch(this.handleError);
  }


  create(organizationaUnit: OrganizationalUnit): Promise<OrganizationalUnit> {
    return this.http
      .post(this.unitsUrl, JSON.stringify(organizationaUnit), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data as OrganizationalUnit)
      .catch(this.handleError);
  }

  update(organizationaUnit: OrganizationalUnit): Promise<OrganizationalUnit> {
    const url = `${this.unitsUrl}/${organizationaUnit.id}`;
    return this.http
      .put(url, JSON.stringify(organizationaUnit), {headers: this.headers})
      .toPromise()
      .then(() => organizationaUnit)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    const url = `${this.unitsUrl}/${id}`;
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
