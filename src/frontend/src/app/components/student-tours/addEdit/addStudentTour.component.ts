import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTour} from "../../../models/student-tour";
import {StudentTourService} from "../../../services/student-tours.service";

@Component({
    selector: "add-studentTour",
    templateUrl: "addEditStudentTour.template.html"
})

export class AddStudentTourComponent implements OnInit {
    public studentTour: StudentTour = new StudentTour();
    public allowRemoval: boolean = false;
    private urlParams: Params;

    constructor(private studentTourService: StudentTourService,
                private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.route.params.subscribe((params: Params) => this.urlParams = params);
    }

    save(): void {
        //TO IMPLEMENT
    }

    private goBack = (): void => {
        this.router.navigateByUrl("persons/" + this.urlParams['personId'] + "/studenttours");
    }
}