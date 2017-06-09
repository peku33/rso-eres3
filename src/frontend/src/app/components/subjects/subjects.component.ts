import {Component, Input, OnInit, ViewChild} from "@angular/core";
import {Subject} from "../../models/subject";
import {SubjectsService} from "../../services/subjects.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {OrganizationalUnit} from "../../models/organizational-unit";
import {UnitsService} from "../../services/units.service";
import {Unit} from "../../models/unit";
import {ModalComponent} from "../modal/modal.component";

@Component({
    selector: "subjects-table",
    templateUrl: "subjects.template.html",
    styleUrls: ['../../styles/table-styles.css', '../../styles/modal-styles.css', '../../styles/textinput-styles.css']
})

export class SubjectsComponent implements OnInit {

    @ViewChild('editSubjectModal') editSubjectModal: ModalComponent;
    @ViewChild('addSubjectModal') addSubjectModal: ModalComponent;


    public subjects: Subject[];
    public subject: Subject = new Subject();
    public currentUnit: OrganizationalUnit = new OrganizationalUnit();
    public exam: boolean = true;
    public loading: boolean = true;

    filteredItems : Subject[];
    pages : number = 4;
    pageSize : number = 25;
    pageNumber : number = 0;
    currentIndex : number = 1;
    pagesIndex : Array<number>;
    pageStart : number = 1;
    inputName : string = '';


    constructor(private subjectsService: SubjectsService, private unitsService: UnitsService, private route: ActivatedRoute, private router: Router) {
        this.getUnit();
    }


    ngOnInit(): void {
        this.getSubjects();


    }

    getSubjects(): void {

        this.loading = true;
        this.route.params.subscribe(params => {
            return this.subjectsService.getSubjectByUnitId(+params['unitId']).then((response: Subject[]) => {
                this.subjects = response;
                this.filteredItems = this.subjects;
                this.paginationInit();
                this.loading = false;

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

    getUnit(): void {
        this.route.params.subscribe(params => {
            return this.unitsService.getUnit(+params['unitId']).then((response: Unit) => {
                this.currentUnit = response;
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

    add(): void {
        if(this.exam == true){
            this.subject.type = "EXAM";
        } else {
            this.subject.type = "NO_EXAM";
        }
        this.subject.unit = this.currentUnit;
        this.subjectsService.create(this.subject).then(() => {
            this.getSubjects();
        }).catch((err) => {
            console.log(err)
            if (err.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (err.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }

    edit(id: number): void {
        console.log(id);
        this.subject = new Subject();
        this.subjectsService.getSubject(id)
            .then((subject: Subject) => {
                console.log(subject);
                this.subject = subject;

                this.exam = subject.type == "EXAM";
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
    }

    delete(id: number): void {
        this.subjectsService.delete(id).then(() => {
            this.getSubjects();
        }).catch((err) => {
            console.log(err)
            if (err.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (err.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }

    save(): void {
        this.subjectsService.update(this.subject).then(() => {
            this.getSubjects();
        }).catch((err) => {
            console.log(err)
            if (err.status === 401) {
                this.router.navigateByUrl("/login");
            }
            if (err.status === 403) {
                this.router.navigateByUrl("/forbidden")
            }
        });
    }

    editSubject(id: number): any {
        this.subject = new Subject();
        this.edit(id);
        this.editSubjectModal.show();
    }

    addSubject(): void {
        this.subject = new Subject();
        this.addSubjectModal.show();
    }

    paginationInit(){
        this.currentIndex = 1;
        this.pageStart = 1;
        this.pages = 4;

        this.pageNumber = parseInt(""+ (this.filteredItems.length / this.pageSize));
        if(this.filteredItems.length % this.pageSize != 0){
            this.pageNumber ++;
        }

        if(this.pageNumber  < this.pages){

            this.pages =  this.pageNumber;

        }



        this.refreshItems();

        console.log("this.pageNumber :  "+this.pageNumber);

    }

    FilterByName(){

        this.filteredItems = [];

        if(this.inputName != ""){

            this.subjects.forEach(element => {
                if(element.fullName.toUpperCase().indexOf(this.inputName.toUpperCase())>=0){
                    this.filteredItems.push(element);
                } else if(element.shortName.toUpperCase().indexOf(this.inputName.toUpperCase())>=0){
                    this.filteredItems.push(element);
                }

            });
        }else{
            this.filteredItems = this.subjects;
        }
        console.log(this.filteredItems);
        this.paginationInit();
    }
    fillArray(): any{
        var obj = new Array();
        for(var index = this.pageStart; index< this.pageStart + this.pages; index ++) {

            obj.push(index);

        }

        return obj;

    }

    refreshItems(){

        this.subjects = this.filteredItems.slice((this.currentIndex - 1)*this.pageSize, (this.currentIndex) * this.pageSize);

        this.pagesIndex =  this.fillArray();

    }

    prevPage(){

        if(this.currentIndex>1){
            this.currentIndex --;
        }
        if(this.currentIndex < this.pageStart){

            this.pageStart = this.currentIndex;

        }

        this.refreshItems();

    }

    nextPage(){

        if(this.currentIndex < this.pageNumber){

            this.currentIndex ++;

        }

        if(this.currentIndex >= (this.pageStart + this.pages)){
            this.pageStart = this.currentIndex - this.pages + 1;
        }

        this.refreshItems();
    }
    setPage(index : number){
        this.currentIndex = index;
        this.refreshItems();
    }
}