import {Component, OnInit} from "@angular/core";
import {Person} from "../../../models/person";
import {PersonsService} from "../../../services/persons.service";
import {UnitsService} from "../../../services/units.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {OrganizationalUnit} from "../../../models/organizational-unit";
import {Location} from '@angular/common';

@Component({
    selector: "edit-person",
    templateUrl: "addEditPerson.component.html"
})

export class EditPersonComponent implements OnInit {
    public person: Person = new Person();
    public allowRemoval: boolean = true;
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
        this.sub = this.route.params.subscribe(params => {
            return this.unitsService.getAllUnits()

                .then((units) => {
                    console.log("units: ", units)
                    this.units = units
                })
                .then(() => {
                    return this.personsService.getPerson(+params['id'])
                })
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
        });
    }

    save(): void {
        this.person.unit = this.selectedUnit
        this.personsService.editPerson(this.person).then(() => {
            this.router.navigateByUrl("/persons");
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
            this.router.navigateByUrl("/persons");
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
}