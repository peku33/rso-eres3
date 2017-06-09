import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTour} from "../../../models/student-tour";
import {Person} from "../../../models/person";
import {OrganizationalUnit} from "../../../models/organizational-unit";
import {StudentTourService} from "../../../services/student-tours.service";
import {UnitsService} from "../../../services/units.service";

@Component({
    selector: "add-studentTour",
    templateUrl: "addEditStudentTour.template.html"
})

export class AddStudentTourComponent {
    public studentTour: StudentTour = new StudentTour();
    public units: any;
    public selectedUnit: OrganizationalUnit = new OrganizationalUnit();
    public allowRemoval: boolean = false;
    private urlParams: Params;
    private sub: any;

    constructor(private studentTourService: StudentTourService,
                private unitsService: UnitsService,
                private router: Router,
                private route: ActivatedRoute) {

        this.studentTour.person = new Person();
        this.studentTour.unit = new OrganizationalUnit();
    }


    ngOnInit(): void {
        this.sub = this.route.params.subscribe(params => {
            this.urlParams = params;
            return this.unitsService.getAllUnits()

                .then((units) => {
                    console.log("units: ",units)
                    this.units = units
                })
                .catch((err) => {
                    console.log(err)
                    if (err.status === 401) {
                        this.router.navigateByUrl("/login");
                    }
                    if (err.status === 403) {
                        this.router.navigateByUrl("/forbidden")
                    }
                })
        });
    }

    saveStudentTour(): void {
        this.studentTour.unit = this.selectedUnit;
        this.studentTour.person.id = this.urlParams['personId'];
        console.log('save: '  + this.studentTour.person.id);
        this.studentTourService.addStudentTour(this.studentTour).then(this.goBack).catch((err) => {
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
        this.router.navigateByUrl("persons/" + this.urlParams['personId'] + "/studenttours");
    }
}