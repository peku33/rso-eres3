import {Component, Input, OnInit} from "@angular/core";
import {StudentTour} from "../../models/student-tour";
import {StudentTourService} from "../../services/student-tours.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import * as _ from "lodash";

@Component({
    selector: "studentTours-tree",
    templateUrl: "student-tours.template.html",
    styleUrls: ['../../styles/table-styles.css']
})
export class StudentToursComponent implements OnInit {
    public studentTours: StudentTour[];

    constructor(private studentTourService: StudentTourService,
                private route: ActivatedRoute, private router: Router) {

    }

    ngOnInit(): void {

        this.route.params.subscribe(params => {
            return this.studentTourService.getAllStudentToursByPersonId(+params['personId'])
                .then((response: StudentTour[]) => {
                this.studentTours = response;
                console.log(this.studentTours);
            })
            .catch((err) => {
                console.log(err)
                if (err.status === 401) {
                    this.router.navigateByUrl("/login");
                }
                if (err.status === 403) {
                    this.router.navigateByUrl("/forbidden")
                }
            });
        });

    }
}