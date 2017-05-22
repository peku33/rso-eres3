import {Component, OnInit} from "@angular/core";
import {Person} from "../../../model/person";
import {PersonsService} from "../../../services/persons.service";
import {Router} from "@angular/router";
import {Unit} from "../../../model/unit";
import {UnitsService} from "../../../services/units.service";
@Component({
    selector: "add-unit",
    templateUrl: "addEditUnit.component.html"
})

export class AddUnitComponent {
    public unit: Unit = new Unit();
    public allowRemoval: boolean = false;

    constructor(private unitsService: UnitsService, private router: Router) {
    }

    save(): void {
        this.unitsService.addUnit(this.unit).then(() => {
            this.router.navigateByUrl("/units");
        }).catch(console.log);
    }
}