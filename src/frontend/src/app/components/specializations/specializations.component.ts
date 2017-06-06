import {Component, Input, OnInit} from "@angular/core";
import {Specialization} from "../../models/specialization";
import {SpecializationsService} from "../../services/specializations.service";
import {ActivatedRoute, Params} from "@angular/router";
import * as _ from "lodash";
@Component({
    selector: "specializations-tree",
    templateUrl: "specializations.component.html",
    styleUrls: ['../../styles/table-styles.css']
})
export class SpecializationsComponent implements OnInit {
    @Input() public specializations: any;
    @Input() public subTable: boolean;

    constructor(private specializationsService: SpecializationsService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        if (this.specializations == null)
            this.route.params
                .switchMap((params: Params) => this.specializationsService.getSpecializationsByUnitId(+params['unitId']))
                .subscribe(specializations => this.specializations = this.unflatten(specializations));
    }

    private unflatten(array: Specialization[], parent?: any, tree?: any): any {
        tree = typeof tree !== 'undefined' ? tree : [];
        parent = typeof parent !== 'undefined' ? parent : {id: null};

        var children = _.filter(array, (child) => {
            return child.superSpecializationId == parent.id;
        });

        if (!_.isEmpty(children)) {
            if (parent.id == null) {
                tree = children;
            } else {
                parent['children'] = children;
            }
            _.each(children, (child) => {
                this.unflatten(array, child)
            });
        }

        return tree;
    }

    private toggleChildren(specialization: any) {
        specialization.showChildren = !specialization.showChildren;
        console.log("DDD");
    }
}