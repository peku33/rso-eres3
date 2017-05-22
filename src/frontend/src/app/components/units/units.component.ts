import {Component} from "@angular/core";
import {Unit} from "../../model/unit";
import {UnitsService} from "../../services/units.service";
@Component({
    selector: "units-table",
    templateUrl: "units.component.html"
})

export class UnitsComponent {
    private units: Unit[];

    constructor(private unitsService: UnitsService){
    }

    ngOnInit(): void {
        this.unitsService.getAllUnits().then((response: Unit[]) => {
            this.units = response;
        }).catch((error) => console.error(error));
    }
}