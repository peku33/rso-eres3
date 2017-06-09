import {Component, OnInit} from "@angular/core";
import {CreditService} from "../../services/credit.service";
import {Credit} from "../../models/credit"
import {ActivatedRoute, Params} from "@angular/router";

@Component({
    selector: "credit",
    templateUrl: "credit.template.html",
    styleUrls: ['../../styles/table-styles.css', '../../styles/modal-styles.css', '../../styles/textinput-styles.css']
})
export class SubjectCreditComponent implements OnInit {
    public credits: Credit[];

    constructor(private creditService: CreditService, private route: ActivatedRoute) {

    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            return this.creditService.getSubjectRealizationCredits(+params['id'])
                .then((response: Credit[]) => {
                    this.credits = response;
                    console.log(this.credits);
                })
                .catch((err) => {
                    console.log(err)
                });
        });
    }
}