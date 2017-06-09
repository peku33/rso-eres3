import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Specialization} from "../../../models/specialization";
import {SpecializationsService} from "../../../services/specializations.service";
import "rxjs/add/operator/switchMap";

@Component({
    selector: "edit-specialization",
    templateUrl: "addEditSpecialization.component.html"
})

export class EditSpecializationComponent implements OnInit {
    public specialization: Specialization = new Specialization();
    public allowRemoval: boolean = true;
    private urlParams: Params;

    constructor(private specializationsService: SpecializationsService,
                private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params.subscribe((params: Params) => this.urlParams = params);
        this.specializationsService.getSpecialization(+this.urlParams['id'])
            .then(specialization => this.specialization = specialization).catch((err) => {
            console.log(err)
            if (err.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (err.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }

    save(): void {
        this.specializationsService.updateSpecialization(this.specialization).then(this.goBack).catch((err) => {
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
        this.specializationsService.deleteSpecialization(this.specialization.id).then(this.goBack).catch((err) => {
            console.log(err)
            if (err.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (err.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }

    private goBack = (): void => {
        this.router.navigateByUrl("units/" + this.urlParams['unitId'] + "/specializations");
    }
}