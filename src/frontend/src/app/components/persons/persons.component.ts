import {Component, OnInit} from "@angular/core";
import {PersonsService} from "../../services/persons.service";
import {Person} from "../../models/person";
@Component({
    selector: "users-table",
    templateUrl: "persons.component.html"
})

export class PersonsComponent implements OnInit{
    private persons: Person[];

    constructor(private personsService: PersonsService){
    }

    ngOnInit(): void {
        this.personsService.getPersons().then((response: Person[]) => {
            this.persons = response;
        }).catch((error) => console.error(error));
    }
}
