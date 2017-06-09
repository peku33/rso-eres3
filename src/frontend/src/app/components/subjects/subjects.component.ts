import {Component, Input, OnInit} from "@angular/core";
import {Subject} from "../../models/subject";
import {SubjectsService} from "../../services/subjects.service";
import {ActivatedRoute, Params, Router} from "@angular/router";

@Component({
    selector: "subjects-table",
    templateUrl: "subjects.template.html",
    styleUrls: ['../../styles/table-styles.css']
})

export class SubjectsComponent implements OnInit {
    public subjects: Subject[];

    constructor(private subjectsService: SubjectsService, private route: ActivatedRoute, private router: Router) {
    }


    ngOnInit(): void {
        this.route.params.subscribe(params => {
            return this.subjectsService.getSubjectByUnitId(+params['unitId']).then((response: Subject[]) => {
                this.subjects = response;
                console.log(this.subjects);
            }).catch((err) => {
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