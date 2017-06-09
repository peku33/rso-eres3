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
        }).catch((err) => {
            console.log(err)
            if (err.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (err.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }
}