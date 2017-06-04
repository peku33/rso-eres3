import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTour} from "../../../models/student-tour";
import {StudentTourService} from "../../../services/student-tours.service";
import "rxjs/add/operator/switchMap";

@Component({
    selector: "edit-studentTour",
    templateUrl: "addEditStudentTour.template.html"
})

export class EditStudentTourComponent implements OnInit {
    public studentTour: StudentTour = new StudentTour();
    public allowRemoval: boolean = true;
    private urlParams: Params;

    constructor(private studentTourService: StudentTourService,
                private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params.subscribe((params: Params) => this.urlParams = params);
        this.studentTourService.getStudentTour(+this.urlParams['personId'])
            .then(studentTour => this.studentTour = studentTour);
    }


    saveStudentTour(): void {
        this.studentTourService.updateStudentTour(this.studentTour).then(this.goBack).catch(console.log);
    }

    deleteStudentTour(): void {
        this.studentTourService.deleteStudentTour(this.studentTour.id).then(this.goBack).catch(console.log);
    }

    private goBack = (): void => {
        this.router.navigateByUrl("persons/" + this.urlParams['personId'] + "/studenttours");
    }
}