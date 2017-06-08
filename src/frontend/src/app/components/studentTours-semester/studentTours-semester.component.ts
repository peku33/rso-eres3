import {Component, OnInit} from "@angular/core";
import {StudentTourSemesterService} from "../../services/studentTour-semester.service";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
    selector: "studentTours-semester",
    templateUrl: "studentTours-semester.template.html"
})

export class StudentToursSemesterComponent implements OnInit {

    constructor(private studentTourSemesterService: StudentTourSemesterService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {

    }
}