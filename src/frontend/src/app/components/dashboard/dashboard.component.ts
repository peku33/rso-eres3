import {Component, OnInit} from "@angular/core";
import {PersonsService} from "../../services/persons.service";
import {Person} from "../../models/person";

@Component({
    selector: "dashboard",
    templateUrl: "dashboard.component.html"
})

export class DashboardComponent implements OnInit {

    constructor(private personsService: PersonsService) {}

    ngOnInit(): void {
        this.personsService.getPersons().then((persons: Person[]) => {
          console.log(persons)
        }).catch((error) => console.error(error));
    }

}
