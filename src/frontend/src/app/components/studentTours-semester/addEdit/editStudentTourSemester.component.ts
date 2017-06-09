import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {StudentTourSemester} from "../../../models/studentTour-semester";
import {Semester} from "../../../models/semester";
import {Specialization} from "../../../models/specialization";
import {StudentTourSemesterService} from "../../../services/studentTour-semester.service";
import {SemestersService} from "../../../services/semesters.service";
import {SpecializationsService} from "../../../services/specializations.service";
import "rxjs/add/operator/switchMap";

@Component({
    selector: "edit-studentTourSemester",
    templateUrl: "addEditStudentTourSemester.template.html"
})

export class EditStudentTourSemesterComponent implements OnInit {
    public studentTourSemester: StudentTourSemester = new StudentTourSemester();
    public tourSemesters: any;
    public selectedSemester: Semester = new Semester();
    public specializations: any;
    public selectedSpecialization: Specialization = this.studentTourSemester.specialization;
    public allowRemoval: boolean = true;
    private urlParams: Params;
    private sub: any;

    constructor(private studentTourSemesterService: StudentTourSemesterService,
                private semestersService: SemestersService,
                private specializationsService: SpecializationsService,
                private router: Router,
                private route: ActivatedRoute) {

        this.studentTourSemester.semester = new Semester();
        this.studentTourSemester.specialization = new Specialization();
    }

    ngOnInit(): void {
        this.sub = this.route.params.subscribe(params => {
            this.urlParams = params;
            return this.semestersService.getSemesters()

                .then((tourSemesters) => {
                    this.tourSemesters = tourSemesters
                })
                .then(() => {
                    return this.studentTourSemesterService.getStudentTourSemester(+this.urlParams['id'])
                })
                .then((studentTourSemester) => {
                    this.studentTourSemester = studentTourSemester;
                    for (let i = 0; i < this.tourSemesters.length; i++) {
                        if(this.tourSemesters[i].id === this.studentTourSemester.semester.id)
                        {
                            this.selectedSemester = this.tourSemesters[i];
                        }
                    }
                    console.log(this.selectedSemester)
                })
                .then(() => {
                    console.log('unitId: ' + this.studentTourSemester.tour.unit.id);
                    return this.specializationsService.getSpecializationsByUnitId(this.studentTourSemester.tour.unit.id)
                        .then((specializations) => {
                            console.log("specializations: ",specializations)
                            this.specializations = specializations;
                            for (let i = 0; i < this.specializations.length; i++) {
                                if(this.specializations[i].id === this.studentTourSemester.specialization.id)
                                {
                                    this.selectedSpecialization = this.specializations[i];
                                }
                            }
                            console.log(this.selectedSpecialization)
                        })
                })
                .catch((err) => {
                    console.log(err)
                })
        });
    }

    saveStudentTourSemester(): void {
        this.studentTourSemester.semester = this.selectedSemester;
        this.studentTourSemester.specialization = this.selectedSpecialization;
        this.studentTourSemesterService.updateStudentTourSemester(this.studentTourSemester).then(this.goBack).catch(console.log);
    }

    deleteStudentTourSemester(): void {
        this.studentTourSemesterService.deleteStudentTourSemester(this.studentTourSemester.id).then(this.goBack).catch(console.log);
    }

    private goBack = (): void => {
        this.router.navigateByUrl("persons/" + this.urlParams['personId'] + "/studenttours/" + this.urlParams['tourId'] + "/studenttoursemesters");
    }
}