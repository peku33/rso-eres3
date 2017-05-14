import {Component, OnInit} from "@angular/core";
import {Person} from "../../../model/person";
import {PersonsService} from "../../../services/persons.service";
import {Router} from "@angular/router";
import {Unit} from "../../../model/unit";
import {UnitsService} from "../../../services/units.service";
@Component({
    selector: "add-edit-unit",
    templateUrl: "addEditUnit.component.html"
})

export class AddEditUnitComponent implements OnInit {
    private unit: Unit = new Unit();

    constructor(private unitsService: UnitsService, private router: Router) {
    }

    ngOnInit(): void {
        // this.personsService.getAllPersons().then((response: Person[]) => {
        //     this.persons = response;
        // }).catch((error) => console.error(error));
    }

    save(): void {
        this.unitsService.addUnit(this.unit).then(() => {
            this.router.navigateByUrl("/units");
        }).catch(console.log);
    }
}