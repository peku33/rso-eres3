import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {Unit} from "../../../models/unit";
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