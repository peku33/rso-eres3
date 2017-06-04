import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTourSemester} from "../../../models/studentTour-semester";
import {StudentTourSemesterService} from "../../../services/studentTour-semester.service";

@Component({
    selector: "add-studentTourSemester",
    templateUrl: "addEditStudentTourSemester.template.html"
})

export class AddStudentTourSemesterComponent implements OnInit {
    private urlParams: Params;

    constructor(private studentTourSemesterService: StudentTourSemesterService,
                private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params.subscribe((params: Params) => this.urlParams = params);
    }

    save(): void {
        //TO IMPLEMENT
    }

    private goBack = (): void => {
        this.router.navigateByUrl("persons/" + this.urlParams['id'] + "/studenttours");
    }
}