import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {AuthenticationService} from "../../services/authentication.service";
import {AlertService} from "../../services/alert.service";


@Component({
    selector: 'login-page',
    templateUrl: 'login-page.template.html',
    styleUrls: ['login-page.style.css']
})

export class LoginPageComponent implements OnInit {
    badAuth = false;
    model: any = {};
    loading = false;
    returnUrl: string;
    title: string = "ERES 3.0 - logowanie"

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertService) { }

    ngOnInit() {
        // reset login status
        this.authenticationService.logout();

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    login() {
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password)
            .then(
                data => {
                    this.router.navigate(['/dashboard']);
                },
                error => {
                    this.badAuth=true;
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}