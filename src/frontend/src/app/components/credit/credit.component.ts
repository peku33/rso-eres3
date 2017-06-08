import {Component, OnInit} from "@angular/core";
import {CreditService} from "../../services/credit.service";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
    selector: "credit",
    templateUrl: "credit.template.html"
})
export class CreditComponent implements OnInit {

    constructor(private creditService: CreditService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {

    }

}