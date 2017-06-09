import {Component, OnInit} from "@angular/core";
import {Person} from "../../../models/person";
import {PersonsService} from "../../../services/persons.service";
import {UnitsService} from "../../../services/units.service";
import {Router, ActivatedRoute} from "@angular/router";
import {OrganizationalUnit} from "../../../models/organizational-unit"
import { Location } from '@angular/common';

@Component({
    selector: "add-person",
    templateUrl: "addPerson.component.html"
})

export class AddPersonComponent implements OnInit{
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
		this.sub = this.route.params.subscribe(params => {
			return this.unitsService.getAllUnits()
		
			.then((units) => {
				console.log("units: ",units)
				this.units = units
			})
			.catch((err) => {
				console.log(err)
			})
        });
    }

    save(): void {
        let password = this.person.pesel + this.person.otherNames
        console.log(password)
        this.person.password = password;
        this.person.unit = this.selectedUnit;

        this.personsService.addPerson(this.person).then(() => {
            this.router.navigateByUrl("/persons");
        }).catch(console.log);
    }

        goBack(): void{
        this._location.back();
    }
}