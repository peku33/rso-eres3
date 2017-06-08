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
            .then(specialization => this.specialization = specialization);
    }

    save(): void {
        this.specializationsService.updateSpecialization(this.specialization).then(this.goBack).catch(console.log);
    }

    delete(): void {
        this.specializationsService.deleteSpecialization(this.specialization.id).then(this.goBack).catch(console.log);
    }

    private goBack = (): void => {
        this.router.navigateByUrl("units/" + this.urlParams['unitId'] + "/specializations");
    }
}