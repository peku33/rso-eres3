import {Component, Input, OnInit} from "@angular/core";
import {Permission} from "../../models/permission";
import {PermissionsService} from "../../services/permissions.service";
import {ActivatedRoute, Params} from "@angular/router";
import * as _ from "lodash";
@Component({
    selector: "permissions",
    templateUrl: "permissions.component.html",
    styleUrls: ['../../styles/table-styles.css']
})
export class PermissionsComponent implements OnInit {
    @Input() public permissions: any;

    constructor(private permissionsService: PermissionsService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
            this.route.params
                .switchMap((params: Params) => this.permissionsService.getPermissions())
                .subscribe((permissions) => {
									console.log(permissions)
                    this.permissions = permissions;
                });
    }
}
