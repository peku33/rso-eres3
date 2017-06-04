import {Component, Input, OnInit} from "@angular/core";
import {StudentTour} from "../../models/student-tour";
import {StudentTourService} from "../../services/student-tours.service";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
    selector: "student-tours",
    templateUrl: "student-tours.template.html"
})
export class StudentToursComponent implements OnInit {
    @Input() public studentTours: any;

    constructor(private studentTourService: StudentTourService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {

    }

}