import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Person } from '../Person/person';

import 'rxjs/add/operator/toPromise';

import { GrantedPermission } from './granted-permission';


@Injectable()
export class PersonService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private grantedPermissionsUrl = 'api/permissions/granted';  // URL to web api
  private personsUrl = 'api/persons';

  constructor(private http: Http) { }

  getGrantedPermissions(): Promise<GrantedPermission[]> {
    return this.http.get(this.grantedPermissionsUrl)
               .toPromise()
               .then(response => response.json().data as GrantedPermission[])
               .catch(this.handleError);
  }


  getGrantedPermissionsByPersonId(id: number): Promise<GrantedPermission> {
    const url = `${this.grantedPermissionsUrl}/person/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as GrantedPermission)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    const url = `${this.grantedPermissionsUrl}/${id}`;
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

  update(person: Person): Promise<Person> {
    const url = `${this.personsUrl}/${person.id}`;
    return this.http
      .put(url, JSON.stringify(person), {headers: this.headers})
      .toPromise()
      .then(() => person)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
