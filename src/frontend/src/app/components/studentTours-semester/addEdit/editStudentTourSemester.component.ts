import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTourSemester} from "../../../models/studentTour-semester";
import {StudentTourSemesterService} from "../../../services/studentTour-semester.service";
import "rxjs/add/operator/switchMap";

@Component({
    selector: "edit-specialization",
    templateUrl: "addEditStudentTourSemester.template.html"
})

export class EditStudentTourSemesterComponent implements OnInit {
    public studentTourSemester: StudentTourSemester = new StudentTourSemester();
    public allowRemoval: boolean = true;
    private urlParams: Params;

    constructor(private studentTourSemesterService: StudentTourSemesterService,
                private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params.subscribe((params: Params) => this.urlParams = params);
        this.studentTourSemesterService.getStudentTourSemester(+this.urlParams['id'])
            .then(studentTourSemester => this.studentTourSemester = studentTourSemester);
    }

    saveStudentTourSemester(): void {
        this.studentTourSemesterService.updateStudentTourSemester(this.studentTourSemester).then(this.goBack).catch(console.log);
    }

    deleteStudentTourSemester(): void {
        this.studentTourSemesterService.deleteStudentTourSemester(this.studentTourSemester.id).then(this.goBack).catch(console.log);
    }

    private goBack = (): void => {
        this.router.navigateByUrl("persons/" + this.urlParams['id'] + "/studenttours");
    }
}