import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTourSemester} from "../../../models/studentTour-semester";
import {StudentTour} from "../../../models/student-tour";
import {Semester} from "../../../models/semester";
import {Specialization} from "../../../models/specialization";
import {Person} from "../../../models/person";
import {StudentTourSemesterService} from "../../../services/studentTour-semester.service";
import {SpecializationsService} from "../../../services/specializations.service";
import {SemestersService} from "../../../services/semesters.service";
import {PersonsService} from "../../../services/persons.service";

@Component({
    selector: "add-studentTourSemester",
    templateUrl: "addEditStudentTourSemester.template.html"
})

export class AddStudentTourSemesterComponent implements OnInit {
    public studentTourSemester: StudentTourSemester = new StudentTourSemester();
    public person: Person;
    public tourSemesters: any;
    public selectedSemester: Semester = new Semester();
    public specializations: any;
    public selectedSpecialization: Specialization = new Specialization();
    private urlParams: Params;
    public allowRemoval: boolean = false;
    private sub: any;
    private unitId: number;

    constructor(private studentTourSemesterService: StudentTourSemesterService,
                private semestersService: SemestersService,
                private specializationsService: SpecializationsService,
                private personsService: PersonsService,
                private router: Router,
                private route: ActivatedRoute) {

        this.studentTourSemester.tour = new StudentTour();
        this.studentTourSemester.semester = new Semester();
        this.studentTourSemester.specialization = new Specialization();
    }

    ngOnInit(): void {
        this.sub = this.route.params.subscribe(params => {
            this.urlParams = params;
            return this.semestersService.getSemesters()
                .then((tourSemesters) => {
                    this.tourSemesters = tourSemesters;
                })
                .then(() =>{
                    return this.personsService.getPerson(+this.urlParams['personId'])
                        .then((person: Person) => {
                            this.unitId = person.unit.id;
                        });
                })
                .then(() => {
                    console.log("this.unitId: ",this.unitId);
                    return this.specializationsService.getSpecializationsByUnitId(this.unitId)
                        .then((specializations) => {
                            this.specializations = specializations;
                        });
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

    saveStudentTourSemester(): void {
        this.studentTourSemester.semester = this.selectedSemester;
        this.studentTourSemester.specialization = this.selectedSpecialization;
        this.studentTourSemester.tour.id = this.urlParams['tourId'];
        this.studentTourSemesterService.addStudentTourSemester(this.studentTourSemester).then(this.goBack).catch((err) => {
            console.log(err)
            if (err.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (err.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }

    private goBack = (): void => {
        this.router.navigateByUrl("persons/" + this.urlParams['personId'] + "/studenttours/" + this.urlParams['tourId'] + "/studenttoursemesters");
    }
}