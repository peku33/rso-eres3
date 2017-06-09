import {Component, OnInit} from "@angular/core";
import {CreditService} from "../../services/credit.service";
import {Credit} from "../../models/credit"
import {ActivatedRoute, Params, Router} from "@angular/router";

@Component({
    selector: "credit",
    templateUrl: "credit.template.html",
    styleUrls: ['../../styles/table-styles.css', '../../styles/modal-styles.css', '../../styles/textinput-styles.css']
})
export class SubjectCreditComponent implements OnInit {
    public credits: Credit[];

    constructor(private creditService: CreditService, private route: ActivatedRoute, private  router: Router) {

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
                    if (err.status === 401) {
                        this.router.navigateByUrl("/login");
                    }
                    if (err.status === 403) {
                        this.router.navigateByUrl("/forbidden")
                    }

                });
        });
    }
}