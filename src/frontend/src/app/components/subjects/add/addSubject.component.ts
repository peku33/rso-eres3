import {Component, Input, OnInit} from "@angular/core";
import {Subject} from "../../../models/subject";
import {SubjectsService} from "../../../services/subjects.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {UnitsService} from "../../../services/units.service";


@Component({
    selector: "subjects-add",
    templateUrl: "addEditSubject.component.html",
    styleUrls: ['../../../styles/table-styles.css']
})

export class AddSubjectComponent implements OnInit {
    public subject: Subject = new Subject();
    public allowRemoval = false;
    public sub: any;
    public unit: any;
    public egzam: any;


    constructor(private subjectsService: SubjectsService, private route: ActivatedRoute,
        			private unitsService: UnitsService,
               private router: Router,) {
    }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
        return this.unitsService.getUnit(+params['unitId'])
        .then((unit) => {
            this.unit = unit;
        })
        .catch((err) => {
            console.log(err)
        })
    });
}

    create(): void {
        this.subject.unit = this.unit;
        this.subjectsService.create(this.subject).then(() => {
        }).catch(console.log);
    }
}