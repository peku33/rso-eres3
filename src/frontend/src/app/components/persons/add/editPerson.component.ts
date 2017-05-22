import {Component, OnInit} from "@angular/core";
import {Person} from "../../../model/person";
import {PersonsService} from "../../../services/persons.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
@Component({
    selector: "edit-person",
    templateUrl: "addEditPerson.component.html"
})

export class EditPersonComponent implements OnInit {
    public person: Person = new Person();
    public allowRemoval: boolean = true;

    constructor(private personsService: PersonsService, private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.personsService.getPerson(+params['id']))
            .subscribe(person => this.person = person);
    }

    save(): void {
        this.personsService.editPerson(this.person).then(() => {
            this.router.navigateByUrl("/persons");
        }).catch(console.log);
    }

    delete(): void {
        this.personsService.deletePerson(this.person.id).then(() => {
            this.router.navigateByUrl("/persons");
        }).catch(console.log);
    }
}