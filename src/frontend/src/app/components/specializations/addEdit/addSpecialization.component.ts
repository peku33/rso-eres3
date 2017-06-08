import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Specialization} from "../../../models/specialization";
import {SpecializationsService} from "../../../services/specializations.service";
@Component({
    selector: "add-specialization",
    templateUrl: "addEditSpecialization.component.html"
})

export class AddSpecializationComponent implements OnInit {
    public specialization: Specialization = new Specialization();
    public allowRemoval: boolean = false;
    private urlParams: Params;

    constructor(private specializationsService: SpecializationsService,
                private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params.subscribe((params: Params) => this.urlParams = params);
    }

    save(): void {
        this.specialization.superSpecializationId = +this.urlParams['id'];
        this.specialization.unitId = +this.urlParams['unitId'];
        this.specializationsService.createSpecialization(this.specialization).then(this.goBack).catch(console.log);
    }

    private goBack = (): void => {
        this.router.navigateByUrl("units/" + this.urlParams['unitId'] + "/specializations");
    }
}