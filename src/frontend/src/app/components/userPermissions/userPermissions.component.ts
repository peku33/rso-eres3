import {Component, OnInit} from "@angular/core";
import {Permission} from "../../models/permission";
import {GrantedPermission} from "../../models/granted-permission"
import {OrganizationalUnit} from "../../models/organizational-unit"
import {PermissionsService} from "../../services/permissions.service";
import {GrantedPermissionsService} from "../../services/granted-permissions.service"
import {UnitsService} from "../../services/units.service"
import {Router, ActivatedRoute} from "@angular/router";
@Component({
    selector: "add-permission",
    templateUrl: "editPermission.component.html"
})

export class UserPermissions implements OnInit{
    public permission: Permission = new Permission();
  	private sub: any;

    constructor(private permissionsService: PermissionsService, private route: ActivatedRoute, private router: Router) {
    }

		ngOnInit(): void {
		this.sub = this.route.params.subscribe(params => {
			this.permissionsService.getPermission(params['name'])
			.then((permission) => {
				this.permission = permission
			})
			.catch((err) => {
				console.log(err)
			})
    });
  }

    editPermission(): void {
        this.permissionsService.update(this.permission).then(() => {
            this.router.navigateByUrl("/permissions");
        }).catch(console.log);
    }

    deletePermission(): void {
			this.permissionsService.delete(this.permission.name)
			.then(() => {
				this.router.navigateByUrl("/permissions");
			})
			.catch((err) => {
				console.log(err)
			})
		}
}