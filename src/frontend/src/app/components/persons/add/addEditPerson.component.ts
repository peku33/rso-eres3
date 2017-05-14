import {Component, OnInit} from "@angular/core";
import {Person} from "../../../model/person";
import {PersonsService} from "../../../services/persons.service";
import {Router} from "@angular/router";
@Component({
    selector: "add-edit-person",
    templateUrl: "addEditPerson.component.html"
})

export class AddEditPersonComponent implements OnInit {
    private person: Person = new Person();

    constructor(private personsService: PersonsService, private router: Router) {
    }

    ngOnInit(): void {
        // this.personsService.getAllPersons().then((response: Person[]) => {
        //     this.persons = response;
        // }).catch((error) => console.error(error));
    }

    save(): void {
        this.personsService.addPerson(this.person).then(() => {
            this.router.navigateByUrl("/persons");
        }).catch(console.log);
    }
}