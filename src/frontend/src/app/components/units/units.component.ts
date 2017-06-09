import {Component, OnInit} from "@angular/core";
import {Unit} from "../../models/unit";
import {UnitsService} from "../../services/units.service";
import {Router} from "@angular/router";
@Component({
    selector: "units-table",
    templateUrl: "units.component.html",
    styleUrls: ['../../styles/table-styles.css']
})

export class UnitsComponent implements OnInit {
    public units: Unit[];

    constructor(private unitsService: UnitsService, private router: Router){
    }

    ngOnInit(): void {
        this.unitsService.getAllUnits().then((response: Unit[]) => {
            this.units = response;
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