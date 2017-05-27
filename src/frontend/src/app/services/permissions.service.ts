import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Permission } from './../models/permission';
import {BE_URL}       from "../settings/backendInfo";



@Injectable()
export class PermissionsService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private permissionsUrl = `${BE_URL}/permissions`;  // URL to web api

  constructor(private http: Http) { }

  getPermissions(): Promise<Permission[]> {
    return this.http.get(this.permissionsUrl)
               .toPromise()
               .then(response => response.json() as Permission[])
               .catch(this.handleError);
  }


  getPermission(name: string): Promise<Permission> {
    const url = `${this.permissionsUrl}/${name}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as Permission)
      .catch(this.handleError);
  }

  create(permission: Permission): Promise<Permission> {
    return this.http
      .post(this.permissionsUrl, JSON.stringify(permission), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as Permission)
      .catch(this.handleError);
  }

  update(permission: Permission): Promise<Permission> {
    const url = `${this.permissionsUrl}`;
    return this.http
      .put(url, JSON.stringify(permission), {headers: this.headers})
      .toPromise()
      .then(() => permission)
      .catch(this.handleError);
  }

  delete(name: string): Promise<void> {
    const url = `${this.permissionsUrl}/${name}`;
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
