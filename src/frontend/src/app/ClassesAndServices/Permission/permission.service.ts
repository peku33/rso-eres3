import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Permission } from './permission';


@Injectable()
export class PermissionService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private permisionUrl = 'api/permissions';  // URL to web api

  constructor(private http: Http) { }

  getPermissions(): Promise<Permission[]> {
    return this.http.get(this.permisionUrl)
               .toPromise()
               .then(response => response.json().data as Permission[])
               .catch(this.handleError);
  }


  getPermission(name: string): Promise<Permission> {
    const url = `${this.permisionUrl}/${name}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as Permission)
      .catch(this.handleError);
  }

  delete(name: string): Promise<void> {
    const url = `${this.permisionUrl}/${name}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  // create(name: string): Promise<Person> {
  //   return this.http
  //     .post(this.personsUrl, JSON.stringify({name: name}), {headers: this.headers})
  //     .toPromise()
  //     .then(res => res.json().data as Person)
  //     .catch(this.handleError);
  // }

  update(permission: Permission): Promise<Permission> {
    const url = `${this.permisionUrl}/${permission.name}`;
    return this.http
      .put(url, JSON.stringify(permission), {headers: this.headers})
      .toPromise()
      .then(() => permission)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
