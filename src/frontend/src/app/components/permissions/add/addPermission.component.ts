import {Component, OnInit} from "@angular/core";
import {Permission} from "../../../models/permission";
import {PermissionsService} from "../../../services/permissions.service";
import {Router} from "@angular/router";
@Component({
    selector: "add-permission",
    templateUrl: "addPermission.component.html"
})

export class AddPermissionComponent {
    public permission: Permission = new Permission();

    constructor(private permissionService: PermissionsService, private router: Router) {
    }

    createPermission(): void {
        this.permissionService.create(this.permission).then(() => {
            this.router.navigateByUrl("/permissions");
        }).catch((err) => {
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