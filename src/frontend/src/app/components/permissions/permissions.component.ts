import {Component, Input, OnInit} from "@angular/core";
import {Permission} from "../../models/permission";
import {PermissionsService} from "../../services/permissions.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import * as _ from "lodash";
@Component({
    selector: "permissions",
    templateUrl: "permissions.component.html",
    styleUrls: ['../../styles/table-styles.css']
})
export class PermissionsComponent implements OnInit {
    @Input() public permissions: any;

    constructor(private permissionsService: PermissionsService, private route: ActivatedRoute, private router: Router) {
    }

    ngOnInit(): void {
            this.route.params
                .switchMap((params: Params) => this.permissionsService.getPermissions().catch((err) => {
                    console.log(err)
                    if (err.status === 401) {
                        this.router.navigateByUrl("/login");
                    }
                    if (err.status === 403) {
                        this.router.navigateByUrl("/forbidden")
                    }
                }))
                .subscribe((permissions) => {
									console.log(permissions)
                    this.permissions = permissions;
                });
    }
}
