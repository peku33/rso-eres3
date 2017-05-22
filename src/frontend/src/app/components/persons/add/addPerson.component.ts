import {Component, OnInit} from "@angular/core";
import {Person} from "../../../model/person";
import {PersonsService} from "../../../services/persons.service";
import {Router} from "@angular/router";
@Component({
    selector: "add-person",
    templateUrl: "addEditPerson.component.html"
})

export class AddPersonComponent {
    private person: Person = new Person();
    private allowRemoval: boolean = false;

    constructor(private personsService: PersonsService, private router: Router) {
    }

    save(): void {
        this.personsService.addPerson(this.person).then(() => {
            this.router.navigateByUrl("/persons");
        }).catch(console.log);
    }
}