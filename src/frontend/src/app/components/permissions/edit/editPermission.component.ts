import {Component, OnInit} from "@angular/core";
import {Permission} from "../../../models/permission";
import {PermissionsService} from "../../../services/permissions.service";
import {Router, ActivatedRoute} from "@angular/router";
@Component({
    selector: "add-permission",
    templateUrl: "editPermission.component.html"
})

export class EditPermissionComponent implements OnInit{
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
}