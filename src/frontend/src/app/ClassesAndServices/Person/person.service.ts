import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Person } from './person';


@Injectable()
export class PersonService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private personsUrl = 'api/persons';  // URL to web api

  constructor(private http: Http) { }

  getPersons(): Promise<Person[]> {
    return this.http.get(this.personsUrl)
               .toPromise()
               .then(response => response.json().data as Person[])
               .catch(this.handleError);
  }


  getPerson(id: number): Promise<Person> {
    const url = `${this.personsUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as Person)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    const url = `${this.personsUrl}/${id}`;
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
