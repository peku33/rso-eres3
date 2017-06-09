import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Unit} from "../../../models/unit";
import {UnitsService} from "../../../services/units.service";
import "rxjs/add/operator/switchMap";

@Component({
    selector: "edit-unit",
    templateUrl: "addEditUnit.component.html"
})

export class EditUnitComponent implements OnInit {
    public unit: Unit = new Unit();
    public allowRemoval: boolean = true;

    constructor(private unitsService: UnitsService, private router: Router, private route: ActivatedRoute,) {
    }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.unitsService.getUnit(+params['id']))
            .subscribe(unit => this.unit = unit);
    }

    save(): void {
        this.unitsService.editUnit(this.unit).then(() => {
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

    delete(): void {
        this.unitsService.deleteUnit(this.unit.id).then(() => {
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