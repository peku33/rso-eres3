import {Component, Input, OnInit, ViewChild} from "@angular/core";
import {Specialization} from "../../models/specialization";
import {SpecializationsService} from "../../services/specializations.service";
import {ActivatedRoute, Params} from "@angular/router";
import * as _ from "lodash";
import {ModalComponent} from "../modal/modal.component";
@Component({
    selector: "specializations-tree",
    templateUrl: "specializations.component.html",
    styleUrls: ['../../styles/table-styles.css', '../../styles/modal-styles.css', '../../styles/textinput-styles.css']
})
export class SpecializationsComponent implements OnInit {
    @ViewChild('addSpecializationModal') addSpecializationModal: ModalComponent;
    @ViewChild('editSpecializationModal') editSpecializationModal: ModalComponent;

    @Input() public specializations: any;
    @Input() public subTable: boolean;
    public unitId;
    public specialization: Specialization = new Specialization();
    private urlParams: Params;


    constructor(private specializationsService: SpecializationsService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {

        if (this.specializations == null) {
            this.route.params
                .switchMap((params: Params) => this.specializationsService.getSpecializationsByUnitId(+params['unitId']))
                .subscribe(specializations => this.specializations = this.unflatten(specializations), (params: Params) => this.urlParams = params)
        }
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

    save(): void {
        this.specialization.superSpecializationId = +this.urlParams['id'];
        this.specialization.unitId = +this.urlParams['unitId'];
        this.specializationsService.createSpecialization(this.specialization).then(() => {
            this.getSpecializations()
        }).catch(console.log);
    }

    addNewSpecialization(): void {
        this.specialization = new Specialization();
        this.addSpecializationModal.show();
    }

    editSpecialization(id): void {
        this.specializationsService.getSpecialization(id)
            .then(specialization => {
                this.specialization = specialization;
                this.editSpecializationModal.show();
            });
    }

    getSpecializations(): void {
        this.route.params
            .switchMap((params: Params) => this.specializationsService.getSpecializationsByUnitId(+params['unitId']))
    }

    update(): void {
        this.specializationsService.updateSpecialization(this.specialization).then(() => {
            this.getSpecializations()
        }).catch(console.log);
    }

    delete(id): void {
        this.specializationsService.deleteSpecialization(id).then(() => {
            this.getSpecializations()
        }).catch(console.log);
    }
}