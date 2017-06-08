import {Component, OnInit} from "@angular/core";
import {Unit} from "../../models/unit";
import {UnitsService} from "../../services/units.service";
@Component({
    selector: "units-table",
    templateUrl: "units.component.html",
    styleUrls: ['../../styles/table-styles.css']
})

export class UnitsComponent implements OnInit {
    public units: Unit[];

    constructor(private unitsService: UnitsService){
    }

    ngOnInit(): void {
        this.unitsService.getAllUnits().then((response: Unit[]) => {
            this.units = response;
        }).catch((error) => console.error(error));
    }
}