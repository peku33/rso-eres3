import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { GrantedPermission } from './../models/granted-permission';
import {BE_URL}              from "../settings/backendInfo";



@Injectable()
export class GrantedPermissionsService {

  private headers = new Headers({'Content-Type': 'application/json'});

  private grantedPermissionsUrl = `${BE_URL}/permissions/granted`;  // URL to web api

  constructor(private http: Http) { }

  getAllGrantedPermissions(): Promise<GrantedPermission[]> {
    return this.http.get(this.grantedPermissionsUrl)
               .toPromise()
               .then(response => response.json().data as GrantedPermission[])
               .catch(this.handleError);
  }


  getGrantedPermissionsByPersonId(id: number): Promise<GrantedPermission[]> {
    const url = `${this.grantedPermissionsUrl}/person/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as GrantedPermission[])
      .catch(this.handleError);
  }

  create(grantedPermission: any): Promise<GrantedPermission> {
    return this.http
      .post(this.grantedPermissionsUrl, JSON.stringify(grantedPermission), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as GrantedPermission)
      .catch(this.handleError);
  }

  delete(grantedPermission: any): Promise<void> {
    const url = `${this.grantedPermissionsUrl}`;
    return this.http.delete(url, {body: JSON.stringify(grantedPermission),   headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
