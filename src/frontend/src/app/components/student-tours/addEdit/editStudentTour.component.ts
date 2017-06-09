import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTour} from "../../../models/student-tour";
import {OrganizationalUnit} from "../../../models/organizational-unit";
import {StudentTourService} from "../../../services/student-tours.service";
import {UnitsService} from "../../../services/units.service";
import "rxjs/add/operator/switchMap";

@Component({
    selector: "edit-studentTour",
    templateUrl: "addEditStudentTour.template.html"
})

export class EditStudentTourComponent implements OnInit {
    public studentTour: StudentTour = new StudentTour();
    public units: any;
    public selectedUnit: OrganizationalUnit = new OrganizationalUnit();
    public allowRemoval: boolean = true;
    private urlParams: Params;
    private sub: any;


    constructor(private studentTourService: StudentTourService,
                private unitsService: UnitsService,
                private router: Router,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.sub = this.route.params.subscribe(params => {
            this.urlParams = params;
            return this.unitsService.getAllUnits()

                .then((units) => {
                    console.log("units: ",units)
                    this.units = units
                })
                .then(() => {
                    return this.studentTourService.getStudentTour(+this.urlParams['id'])
                })
                .then((studentTour) => {
                    this.studentTour = studentTour;
                    for (let i = 0; i < this.units.length; i++) {
                        if(this.units[i].id === this.studentTour.unit.id)
                        {
                            this.selectedUnit = this.units[i];
                        }
                    }
                    console.log(this.selectedUnit)
                })
                .catch((err) => {
                    console.log(err)
                })
        });

    }

    saveStudentTour(): void {
        this.studentTour.unit = this.selectedUnit;
        this.studentTourService.updateStudentTour(this.studentTour).then(this.goBack).catch(console.log);
    }

    deleteStudentTour(): void {
        this.studentTourService.deleteStudentTour(this.studentTour.id).then(this.goBack).catch(console.log);
    }

    private goBack = (): void => {
        this.router.navigateByUrl("persons/" + this.urlParams['personId'] + "/studenttours");
    }
}