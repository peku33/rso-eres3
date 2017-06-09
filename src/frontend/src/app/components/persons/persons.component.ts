import {Component, OnInit, ElementRef, ViewChild} from "@angular/core";
import {PersonsService} from "../../services/persons.service";
import {Person} from "../../models/person";
import {UnitsService} from "../../services/units.service";
import {OrganizationalUnit} from "../../models/organizational-unit"
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';
import {ModalComponent} from "../modal/modal.component";
declare var componentHandler: any;

@Component({
    selector: "persons-table",
    templateUrl: "persons.component.html",
    styleUrls: ['../../styles/table-styles.css', '../../styles/modal-styles.css', '../../styles/textinput-styles.css']
})

export class PersonsComponent implements OnInit {
    @ViewChild('editPersonModal') editPersonModal: ModalComponent;
    @ViewChild('addPersonModal') addPersonModal: ModalComponent;

    public persons: Person[];

    public person: Person = new Person();
    public allowRemoval: boolean = false;
    private sub: any;
    public units: any;
    public selectedUnit: OrganizationalUnit = new OrganizationalUnit();


    constructor(private personsService: PersonsService,
                private unitsService: UnitsService,
                private router: Router,
                private route: ActivatedRoute,
                private _location: Location) {
    }


    ngOnInit(): void {
        this.getPersons();
    }

    getPersons(): void {
        this.personsService.getAllPersons().then((response: Person[]) => {
            this.persons = response;
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

    getUnits(): boolean {
        this.unitsService.getAllUnits()

            .then((units) => {
                console.log("units: ", units)
                this.units = units
                return true
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
        return false
    }

    add(): void {
        let password = this.person.pesel + this.person.otherNames
        console.log(password)
        this.person.password = password;
        this.person.unit = this.selectedUnit;

        this.personsService.addPerson(this.person).then(() => {
            this.getPersons()
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

    edit(id: number): any {
        this.person = new Person();
        this.personsService.getPerson(id)
            .then((person) => {
                this.person = person;
                for (let i = 0; i < this.units.length; i++) {
                    if (this.units[i].id === this.person.unit.id) {
                        this.selectedUnit = this.units[i];
                    }
                }
                console.log(this.selectedUnit)
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
    }

    save(): void {
        this.person.unit = this.selectedUnit
        this.personsService.editPerson(this.person).then(() => {
            this.getPersons()
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

    delete(): void {
        this.personsService.deletePerson(this.person.id).then(() => {
            this.getPersons()
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

    goBack(): void {
        this._location.back();
    }

    editPerson(id: number): any {
        this.person = new Person();
        this.getUnits();
        this.edit(id);
        this.editPersonModal.show();
    }

    addPerson(): void {
        this.person = new Person();
        this.getUnits();
        this.addPersonModal.show();
    }
}