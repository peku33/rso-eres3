import {Component, OnInit} from "@angular/core";
import {StudentTourSemesterService} from "../../services/studentTour-semester.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTourSemester} from "../../models/studentTour-semester";

@Component({
    selector: "studentTours-semester",
    templateUrl: "studentTours-semester.template.html",
    styleUrls: ['../../styles/table-styles.css']
})

export class StudentToursSemesterComponent implements OnInit {
    public studentToursSemester: StudentTourSemester[];


    constructor(private studentTourSemesterService: StudentTourSemesterService,
                private route: ActivatedRoute, private router: Router) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            console.log('tourId: ' + params['tourId']);
            return this.studentTourSemesterService.getAllStudentToursSemestersByTourId(+params['tourId'])
                .then((response: StudentTourSemester[]) => {
                    this.studentToursSemester = response;
                    console.log('studentToursSemester: ' + response);
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
}