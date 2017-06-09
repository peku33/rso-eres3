import {Component, OnInit} from "@angular/core";
import {Permission} from "../../models/permission";
import {Person} from "../../models/person"
import {GrantedPermission} from "../../models/granted-permission"
import {OrganizationalUnit} from "../../models/organizational-unit"
import {PermissionsService} from "../../services/permissions.service";
import {GrantedPermissionsService} from "../../services/granted-permissions.service"
import {PersonsService} from "../../services/persons.service"
import {UnitsService} from "../../services/units.service"
import {Router, ActivatedRoute} from "@angular/router";
@Component({
    selector: "user-permissions",
    templateUrl: "userPermissions.component.html"
})

export class UserPermissions implements OnInit{
    public permissions: any;
	public units: any;
	public grantedPermissions: any;
	public person: Person = new Person()
	public selectedUnit: OrganizationalUnit = new OrganizationalUnit();
	public selectedPermission: Permission = new Permission();

  	private sub: any;

    constructor(private personsService:PersonsService, 
				private permissionsService: PermissionsService,
				private grantedPermissionsService: GrantedPermissionsService,
				private unitsService: UnitsService,
	 			private route: ActivatedRoute, private router: Router) {
    }

		ngOnInit(): void {
		this.sub = this.route.params.subscribe(params => {
			return this.personsService.getPerson(+params['id'])
			.then((person) => {
				console.log("Person", person)
				this.person = person
			})
			.then(() => {
				return this.permissionsService.getPermissions()
			})
			.then((permissions) => {
				console.log("Permissions", permissions)
				this.permissions = permissions
			})
			.then(() => {
				return this.grantedPermissionsService.getGrantedPermissionsByPersonId(+params['id'])
			})
			.then((grantedPermissions) =>{
				console.log("Granted:", grantedPermissions)
				this.grantedPermissions = grantedPermissions
			})
			.then(() =>{
				return this.unitsService.getAllUnits()
			})
			.then((units) => {
				console.log("units: ",units)
				this.units = units
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

    grantPermission(): void {
		let newPermission: any
		newPermission =  {
			permission: this.selectedPermission.name,
			unit: this.selectedUnit.id,
			person: this.person.id
		}
		this.grantedPermissionsService.create(newPermission)
		.then(() => {
			this.ngOnInit()
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

	revokePermission(granted: GrantedPermission): void {
		let permissionToDelete: any;
		permissionToDelete = 
		{
			permission: granted.permission.name,
			unit: granted.unit.id,
			person: granted.person.id
		}
		console.log(granted)
		this.grantedPermissionsService.delete(permissionToDelete)
		.then(() => {
			this.ngOnInit()
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

}