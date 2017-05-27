import {Component, OnInit} from "@angular/core";
import {PersonsService} from "../../services/persons.service";
import {Person} from "../../model/person";
@Component({
    selector: "persons-table",
    templateUrl: "persons.component.html"
})

export class PersonsComponent implements OnInit {
    public persons: Person[];

    constructor(private usersService: PersonsService){
    }

    ngOnInit(): void {
        this.usersService.getAllPersons().then((response: Person[]) => {
            this.persons = response;
        }).catch((error) => console.error(error));
    }
}