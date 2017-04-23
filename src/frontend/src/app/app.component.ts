import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {Person} from "./person";
import "rxjs/add/operator/toPromise";


@Component({
    selector: 'my-app',
    template: `
        <h1>My App: {{name}}</h1>
        <router-outlet></router-outlet>
    `
})
export class AppComponent implements OnInit {
    name = 'eres 3.0';
    people: Person[];

    private personsUrl = 'http://localhost:8080/persons';

    constructor(private http: Http) {
    }

    // Line below are temporary, for testing the connection with BE
    ngOnInit(): void {
        //this.getPersons().then(people => this.people = people);
    }

    getPersons(): Promise<Person[]> {
        return this.http.get(this.personsUrl)
            .toPromise()
            .then(response => response.json().data as Person[])
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}